package kfinance.common.calendar.holiday.impl

import com.google.gson.Gson
import kfinance.common.calendar.holiday.Country
import kfinance.common.calendar.holiday.Holiday
import kfinance.common.calendar.holiday.HolidayFinder
import kfinance.common.calendar.holiday.impl.HolidayFinderImplDto.*
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.format.DateTimeFormatter

class HolidayFinderImpl : HolidayFinder {
    private val httpClient: OkHttpClient = OkHttpClient()
    private val gson = Gson()

    override fun getSupportedCountries(): List<Country> {
        val url: HttpUrl = SUPPORTED_COUNTRIES_URL.toHttpUrl()
        val request = Request.Builder().url(url).build()
        val response = httpClient.newCall(request).execute()
        val body: List<CountryInfo> = gson.fromJson(response.body?.string(), Array<CountryInfo>::class.java).toList()
        return body.map {
            Country(
                countryCode = it.countryCode,
                countryName = it.fullName,
            )
        }
    }

    override fun isSupportedCountry(countryCode: String): Boolean {
        return this.getSupportedCountries().map { it.countryCode }.contains(countryCode)
    }

    override fun getHolidaysForYear(
        year: Year,
        countryCode: String,
        region: String?,
    ): List<Holiday> {
        val url =
            HOLIDAYS_FOR_YEAR_URL.toHttpUrlOrNull()!!.newBuilder()
                .addQueryParameter("year", year.toString())
                .addQueryParameter("country", countryCode)
        region?.let {
            url.addQueryParameter("region", it)
        }
        val request = Request.Builder().url(url.build()).build()
        val response = httpClient.newCall(request).execute()
        val body: List<HolidayInfo> = gson.fromJson(response.body?.string(), Array<HolidayInfo>::class.java).toList()
        return body.map { it.toHoliday() }
    }

    override fun getHolidaysForMonth(
        year: Year,
        month: Month,
        countryCode: String,
        region: String?,
    ): List<Holiday> {
        val url =
            HOLIDAYS_FOR_MONTH_URL.toHttpUrlOrNull()!!.newBuilder()
                .addQueryParameter("year", year.toString())
                .addQueryParameter("month", month.value.toString())
                .addQueryParameter("country", countryCode)
        region?.let {
            url.addQueryParameter("region", it)
        }
        val request = Request.Builder().url(url.build()).build()
        val response = httpClient.newCall(request).execute()
        val body: List<HolidayInfo> = gson.fromJson(response.body?.string(), Array<HolidayInfo>::class.java).toList()
        return body.map { it.toHoliday() }
    }

    override fun isPublicHoliday(
        date: LocalDate,
        countryCode: String,
        region: String?,
    ): Boolean {
        val url =
            IS_PUBLIC_HOLIDAY_URL.toHttpUrlOrNull()!!.newBuilder()
                .addQueryParameter("date", date.format(FORMATTER))
                .addQueryParameter("country", countryCode)
        region?.let {
            url.addQueryParameter("region", it)
        }
        val request: Request = Request.Builder().url(url.build()).build()
        val response = httpClient.newCall(request).execute()
        val body: IsPublicHolidayResponse = gson.fromJson(response.body?.string(), IsPublicHolidayResponse::class.java)
        return body.isPublicHoliday
    }

    override fun getHolidaysForDateRange(
        fromDate: LocalDate,
        toDate: LocalDate,
        countryCode: String,
        region: String?,
    ): List<Holiday> {
        val url =
            HOLIDAYS_FOR_DATE_RANGE_URL.toHttpUrlOrNull()!!.newBuilder()
                .addQueryParameter("fromDate", fromDate.format(FORMATTER))
                .addQueryParameter("toDate", toDate.format(FORMATTER))
                .addQueryParameter("country", countryCode)
        region?.let {
            url.addQueryParameter("region", it)
        }
        val request = Request.Builder().url(url.build()).build()
        val response = httpClient.newCall(request).execute()
        val body: List<HolidayInfo> = gson.fromJson(response.body?.string(), Array<HolidayInfo>::class.java).toList()
        return body.map { it.toHoliday() }
    }

    companion object {
        private const val API_HOST = "https://kayaposoft.com/enrico/json/v3.0"
        private const val SUPPORTED_COUNTRIES_URL = "$API_HOST/getSupportedCountries"
        private const val HOLIDAYS_FOR_YEAR_URL = "$API_HOST/getHolidaysForYear"
        private const val HOLIDAYS_FOR_MONTH_URL = "$API_HOST/getHolidaysForMonth"
        private const val IS_PUBLIC_HOLIDAY_URL = "$API_HOST/isPublicHoliday"
        private const val HOLIDAYS_FOR_DATE_RANGE_URL = "$API_HOST/getHolidaysForDateRange"

        private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    }
}
