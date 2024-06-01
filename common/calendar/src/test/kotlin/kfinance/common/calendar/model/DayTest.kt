package kfinance.common.calendar.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.DayOfWeek
import java.time.LocalDate

class DayTest {
    // 2024-06-01 is Saturday
    @DisplayName("date를 기준으로 요일을 반환한다")
    @Test
    fun dayOfWeekShouldBeReturnedBasedOnDate() {
        // given
        val date = LocalDate.of(2024, 6, 1)
        val day = Day(date, "kr", false)

        // when
        val dayOfWeek = day.dayOfWeek()

        // then
        assertEquals(DayOfWeek.SATURDAY, dayOfWeek)
    }

    // 2024-06-01 is Saturday
    // 2024-06-02 is Sunday
    // 2024-06-03 is Monday
    @DisplayName("date가 주말인지 여부를 반환한다")
    @ParameterizedTest
    @CsvSource(
        "2024-06-01, true",
        "2024-06-02, true",
        "2024-06-03, false",
    )
    fun isWeekendShouldReturnTrueIfDateIsWeekend(
        input: String,
        expected: Boolean,
    ) {
        // given
        val date = LocalDate.parse(input)
        val day = Day(date, "kr", false)

        // when
        val isWeekend = day.isWeekend()

        // then
        assertEquals(expected, isWeekend)
    }

    // 2023-12-31 is Sunday. and it is not a public holiday
    // 2024-01-01 is Monday. but it is a public holiday
    // 2024-01-02 is Tuesday. and it is not a public holiday
    @DisplayName("date가 휴일인지 여부를 반환한다")
    @ParameterizedTest
    @CsvSource(
        "2023-12-31, false, true",
        "2024-01-01, true, true",
        "2024-01-02, false, false",
    )
    fun isHolidayShouldReturnTrueIfDateIsHoliday(
        input: String,
        isPublicHoliday: Boolean,
        expected: Boolean,
    ) {
        // given
        val date = LocalDate.parse(input)
        val day = Day(date, "kr", isPublicHoliday)

        // when
        val isHoliday = day.isHoliday()

        // then
        assertEquals(expected, isHoliday)
    }
}
