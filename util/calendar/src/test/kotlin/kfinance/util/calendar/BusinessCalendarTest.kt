package kfinance.util.calendar

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.temporal.ChronoUnit

class BusinessCalendarTest {
    private var businessCalendar = BusinessCalendar()

    @DisplayName("영업일 및 공휴일 검색을 지원하는 나라인지 여부를 반환한다")
    @Test
    fun isSupportedCountryShouldReturnTrueOrFalse() {
        // given
        // when
        val actual = businessCalendar.isSupportedCountry("kor")

        // then
        assert(actual)
    }

    @DisplayName("특정 날짜에 대해 휴일 여부를 반환한다")
    @Test
    fun isHolidayShouldReturnTrueOrFalse() {
        // given
        val date = LocalDate.of(2024, 1, 1)

        // when
        val actual = businessCalendar.isHoliday(date)

        // then
        assert(actual)
    }

    @DisplayName("특정 날짜에 대해 공휴일 여부를 반환한다")
    @Test
    fun isPublicHolidayShouldReturnTrueOrFalse() {
        // given
        val date = LocalDate.of(2024, 1, 1)

        // when
        val actual = businessCalendar.isPublicHoliday(date)

        // then
        assert(actual)
    }

    @DisplayName("특정 연도의 날짜 목록을 반환한다")
    @Test
    fun getDaysForYearShouldReturnListOfDays() {
        // given
        val year = Year.of(2024)

        // when
        val days = businessCalendar.getDaysForYear(year).days

        // then
        val dayAbout1stJanuary2024 = days.first()
        assert(dayAbout1stJanuary2024.date == LocalDate.of(2024, 1, 1))
        assert(dayAbout1stJanuary2024.dayOfWeek() == DayOfWeek.MONDAY)
        assert(dayAbout1stJanuary2024.isHoliday())

        val dayAbout31stDecember2024 = days.last()
        println(dayAbout31stDecember2024.date)
        assert(dayAbout31stDecember2024.date == LocalDate.of(2024, 12, 31))
        assert(dayAbout31stDecember2024.dayOfWeek() == DayOfWeek.TUESDAY)
        assert(!dayAbout31stDecember2024.isHoliday())
    }

    @DisplayName("특정 연도의 공휴일 목록을 반환한다")
    @Test
    fun getPublicHolidayListForYearShouldReturnListOfDays() {
        // given
        val year = Year.of(2024)

        // when
        val holidays = businessCalendar.getPublicHolidaysForYear(year).days

        // then
        val holidayAbout1stJanuary2024 = holidays.first()
        assert(holidayAbout1stJanuary2024.date == LocalDate.of(2024, 1, 1))
        assert(holidayAbout1stJanuary2024.dayOfWeek() == DayOfWeek.MONDAY)
        assert(holidayAbout1stJanuary2024.isHoliday())

        val holidayAbout25thDecember2024 = holidays.last()
        assert(holidayAbout25thDecember2024.date == LocalDate.of(2024, 12, 25))
        assert(holidayAbout25thDecember2024.dayOfWeek() == DayOfWeek.WEDNESDAY)
        assert(holidayAbout25thDecember2024.isHoliday())
    }

    @DisplayName("특정 월의 날짜 목록을 반환한다")
    @Test
    fun getDaysForMonthShouldReturnListOfDays() {
        // given
        val year = Year.of(2024)
        val month = Month.of(1)

        // when
        val days = businessCalendar.getDaysForMonth(year, month).days

        // then
        val first = days.first()
        assert(first.date == LocalDate.of(2024, 1, 1))
        assert(first.dayOfWeek() == DayOfWeek.MONDAY)
        assert(first.isHoliday())

        val last = days.last()
        assert(last.date == LocalDate.of(2024, 1, 31))
        assert(last.dayOfWeek() == DayOfWeek.WEDNESDAY)
        assert(!last.isHoliday())
    }

    @DisplayName("특정 월의 공휴일 목록을 반환한다")
    @Test
    fun getPublicHolidaysForMonthShouldReturnListOfDays() {
        // given
        val year = Year.of(2024)
        val month = Month.of(1)

        // when
        val holidays = businessCalendar.getPublicHolidaysForMonth(year, month).days

        // then
        assert(holidays.size == 1)

        val first = holidays.first()
        assert(first.date == LocalDate.of(2024, 1, 1))
        assert(first.dayOfWeek() == DayOfWeek.MONDAY)
        assert(first.isHoliday())
    }

    @DisplayName("특정 날짜 범위의 날짜 목록을 반환한다")
    @Test
    fun getDaysForRangeShouldReturnListOfDays() {
        // given
        val from = LocalDate.of(2023, 12, 25)
        val to = LocalDate.of(2024, 1, 25)

        // when
        val days = businessCalendar.getDaysForDateRange(from, to).days

        // then
        assert(days.size == ChronoUnit.DAYS.between(from, to).toInt() + 1)

        val first = days.first()
        assert(first.date == from)
        assert(first.dayOfWeek() == DayOfWeek.MONDAY)
        assert(first.isHoliday())

        val last = days.last()
        println(last.date)
        assert(last.date == to)
        assert(last.dayOfWeek() == DayOfWeek.THURSDAY)
        assert(!last.isHoliday())
    }

    @DisplayName("특정 날짜 범위의 공휴일 목록을 반환한다")
    @Test
    fun getPublicHolidaysForRangeShouldReturnListOfDays() {
        // given
        val from = LocalDate.of(2023, 12, 25)
        val to = LocalDate.of(2024, 1, 25)

        // when
        val holidays = businessCalendar.getHolidaysForDateRange(from, to).days

        // then
        assert(holidays.size == 2)

        val first = holidays.first()
        assert(first.date == LocalDate.of(2023, 12, 25))
        assert(first.dayOfWeek() == DayOfWeek.MONDAY)
        assert(first.isHoliday())

        val last = holidays.last()
        assert(last.date == LocalDate.of(2024, 1, 1))
        assert(last.dayOfWeek() == DayOfWeek.MONDAY)
        assert(last.isHoliday())
    }
}
