package kfinance.util.calendar.holiday.impl

import kfinance.util.calendar.holiday.Holiday
import java.time.LocalDate
import java.util.*

class HolidayFinderImplDto {
    data class IsPublicHolidayResponse(
        val isPublicHoliday: Boolean,
    )

    data class CountryInfo(
        val countryCode: String,
        val regions: List<String>,
        val holidayTypes: List<String>,
        val fullName: String,
        val fromDate: Date,
        val toDate: Date,
    )

    data class Date(
        val day: Int,
        val month: Int,
        val year: Int,
        val dayOfWeek: Int? = LocalDate.of(year, month, day).dayOfWeek.value,
    ) {
        fun toLocalDate(): LocalDate {
            return LocalDate.of(year, month, day)
        }
    }

    data class HolidayInfo(
        val date: Date,
        val name: List<Note>,
        val holidayType: String,
        val flags: List<String>? = null,
        val note: List<Note>? = null,
    ) {
        fun toHoliday(): Holiday {
            val localeName: Note = name.firstOrNull { it.lang != "en" } ?: name.first()
            return Holiday(
                date = date.toLocalDate(),
                locale = Locale(localeName.lang),
                type = holidayType,
                description = localeName.text,
            )
        }
    }

    data class Note(
        val lang: String,
        val text: String,
    )
}
