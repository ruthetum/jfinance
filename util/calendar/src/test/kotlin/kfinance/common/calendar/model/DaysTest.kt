package kfinance.common.calendar.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

class DaysTest {
    // 2024-05-01 is Wednesday
    // 2024-05-02 is Thursday
    // 2024-05-03 is Friday
    // 2024-05-04 is Saturday
    // 2024-05-05 is Sunday. and it is public holidays
    // 2024-05-06 is Monday. but it is replaced holidays
    // 2024-05-07 is Tuesday
    private val days =
        Days(
            listOf(
                Day(
                    LocalDate.of(2024, 5, 1),
                    "kr",
                    false,
                ),
                Day(
                    LocalDate.of(2024, 5, 2),
                    "kr",
                    false,
                ),
                Day(
                    LocalDate.of(2024, 5, 3),
                    "kr",
                    false,
                ),
                Day(
                    LocalDate.of(2024, 5, 4),
                    "kr",
                    false,
                ),
                Day(
                    LocalDate.of(2024, 5, 5),
                    "kr",
                    true,
                ),
                Day(
                    LocalDate.of(2024, 5, 6),
                    "kr",
                    true,
                ),
                Day(
                    LocalDate.of(2024, 5, 7),
                    "kr",
                    false,
                ),
            ),
        )

    @DisplayName("영업일만 반환한다")
    @Test
    fun daysShouldReturnOnlyBusinessDays() {
        // given

        // when
        val businessDays = days.getBusinessDays()

        // then
        assertEquals(4, businessDays.size)
        assert(businessDays[0].date == LocalDate.of(2024, 5, 1))
        assert(businessDays[1].date == LocalDate.of(2024, 5, 2))
        assert(businessDays[2].date == LocalDate.of(2024, 5, 3))
        assert(businessDays[3].date == LocalDate.of(2024, 5, 7))
    }

    @DisplayName("공휴일만 반환한다")
    @Test
    fun daysShouldReturnOnlyHolidays() {
        // given

        // when
        val holidays = days.getHolidays()

        // then
        assertEquals(3, holidays.size)
        assert(holidays[0].date == LocalDate.of(2024, 5, 4))
        assert(holidays[1].date == LocalDate.of(2024, 5, 5))
        assert(holidays[2].date == LocalDate.of(2024, 5, 6))
    }
}
