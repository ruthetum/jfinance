package kfinance.util.calendar.holiday

import java.time.LocalDate
import java.time.Month
import java.time.Year

interface HolidayFinder {
    fun getSupportedCountries(): List<Country>

    fun isSupportedCountry(countryCode: String): Boolean

    fun getHolidaysForYear(
        year: Year,
        countryCode: String,
        region: String? = null,
    ): List<Holiday>

    fun getHolidaysForMonth(
        year: Year,
        month: Month,
        countryCode: String,
        region: String? = null,
    ): List<Holiday>

    fun isPublicHoliday(
        date: LocalDate,
        countryCode: String,
        region: String? = null,
    ): Boolean

    fun getHolidaysForDateRange(
        fromDate: LocalDate,
        toDate: LocalDate,
        countryCode: String,
        region: String? = null,
    ): List<Holiday>
}
