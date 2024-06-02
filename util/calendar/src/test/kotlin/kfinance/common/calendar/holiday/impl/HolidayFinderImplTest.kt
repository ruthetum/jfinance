package kfinance.common.calendar.holiday.impl

import kfinance.common.calendar.holiday.HolidayFinder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDate
import java.time.Month
import java.time.Year

class HolidayFinderImplTest {
    private lateinit var finder: HolidayFinder

    @BeforeEach
    fun setUp() {
        finder = HolidayFinderImpl()
    }

    @DisplayName("공휴일 검색을 지원하는 나라를 조회한다")
    @Test
    fun countryListToSupportShouldBeReturned() {
        // given

        // when
        val countries = finder.getSupportedCountries()

        // then
        assert(countries.isNotEmpty())
        assert(countries.first().countryCode.isNotBlank())
        assert(countries.first().countryName.isNotBlank())
    }

    @DisplayName("공휴일 검색을 지원하는 나라인지 여부를 참/거짓으로 반환한다")
    @ParameterizedTest
    @CsvSource(
        "kr, true",
        "usa, true",
        "jpn, true",
        "chn, true",
        "zzz, false",
    )
    fun isSupportedCountryShouldReturnTrueOrFalse(
        input: String,
        expected: Boolean,
    ) {
        // given
        // when
        val actual = finder.isSupportedCountry(input)

        // then
        assertEquals(expected, actual)
    }

    @DisplayName("특정 나라의 특정 연도의 공휴일을 조회한다")
    @Test
    fun holidaysForYearShouldBeReturned() {
        // given
        val year = Year.of(2022)
        val countryCode = "kr"

        // when
        val holidays = finder.getHolidaysForYear(year, countryCode)

        // then
        assert(holidays.isNotEmpty())

        val dates = holidays.map { it.date }
        assertEquals(
            true,
            dates.contains(LocalDate.of(2022, 1, 1)),
        )
        assertEquals(
            true,
            dates.contains(LocalDate.of(2022, 12, 25)),
        )
        assertEquals(
            false,
            dates.contains(LocalDate.of(2023, 1, 1)),
        )
    }

    @DisplayName("특정 나라의 특정 월의 공휴일을 조회한다")
    @Test
    fun holidaysForMonthShouldBeReturned() {
        // given
        val year = Year.of(2022)
        val month = Month.of(1)
        val countryCode = "kr"

        // when
        val holidays = finder.getHolidaysForMonth(year, month, countryCode)

        // then
        assert(holidays.isNotEmpty())

        val dates = holidays.map { it.date }
        assertEquals(
            true,
            dates.contains(LocalDate.of(2022, 1, 1)),
        )
        assertEquals(
            false,
            dates.contains(LocalDate.of(2022, 12, 25)),
        )
    }

    @DisplayName("특정 나라의 특정 날짜의 공휴일 여부를 조회한다")
    @Test
    fun isPublicHolidayShouldReturnTrueOrFalse() {
        // given
        val date = LocalDate.of(2022, 1, 1)
        val countryCode = "kr"

        // when
        val actual = finder.isPublicHoliday(date, countryCode)

        // then
        assertEquals(true, actual)
    }

    @DisplayName("특정 나라의 특정 날짜 범위의 공휴일을 조회한다")
    @Test
    fun holidaysForDateRangeShouldBeReturned() {
        // given
        val fromDate = LocalDate.of(2023, 12, 10)
        val toDate = LocalDate.of(2024, 1, 10)
        val countryCode = "kr"

        // when
        val holidays = finder.getHolidaysForDateRange(fromDate, toDate, countryCode)

        // then
        assert(holidays.isNotEmpty())

        val dates = holidays.map { it.date }
        assertEquals(
            false,
            dates.contains(LocalDate.of(2023, 1, 1)),
        )
        assertEquals(
            true,
            dates.contains(LocalDate.of(2023, 12, 25)),
        )
        assertEquals(
            true,
            dates.contains(LocalDate.of(2024, 1, 1)),
        )
        assertEquals(
            false,
            dates.contains(LocalDate.of(2024, 5, 5)),
        )
    }
}
