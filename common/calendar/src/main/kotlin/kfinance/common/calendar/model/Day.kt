package kfinance.common.calendar.model

import java.time.DayOfWeek
import java.time.LocalDate

data class Day(
    val date: LocalDate,
    val countryCode: String,
    val isPublicHoliday: Boolean,
    val type: String? = null,
    val description: String? = null,
) : Comparable<Day> {
    fun dayOfWeek(): DayOfWeek {
        return date.dayOfWeek
    }

    fun isWeekend(): Boolean {
        return date.isWeekend()
    }

    fun isHoliday(): Boolean {
        return isPublicHoliday || isWeekend()
    }

    override fun compareTo(other: Day): Int {
        return date.compareTo(other.date)
    }
}

fun LocalDate.isWeekend(): Boolean {
    return this.dayOfWeek == DayOfWeek.SATURDAY || this.dayOfWeek == DayOfWeek.SUNDAY
}
