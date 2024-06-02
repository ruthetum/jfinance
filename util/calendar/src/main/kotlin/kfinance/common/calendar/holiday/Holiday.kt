package kfinance.common.calendar.holiday

import kfinance.common.calendar.model.Day
import java.time.LocalDate
import java.util.*

data class Holiday(
    val date: LocalDate,
    val locale: Locale,
    val type: String,
    val description: String,
) {
    fun toDay(): Day {
        return Day(date, locale.country, true, type, description)
    }
}
