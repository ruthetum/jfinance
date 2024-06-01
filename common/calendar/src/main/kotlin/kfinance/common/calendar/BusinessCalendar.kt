package kfinance.common.calendar

import kfinance.common.calendar.holiday.Holiday
import kfinance.common.calendar.holiday.HolidayFinder
import kfinance.common.calendar.holiday.impl.HolidayFinderImpl
import kfinance.common.calendar.model.Day
import kfinance.common.calendar.model.Days
import kfinance.common.calendar.model.isWeekend
import java.time.LocalDate
import java.time.Month
import java.time.Year
import java.time.temporal.ChronoUnit

class BusinessCalendar(
    private val defaultCountryCode: String = "kor",
    private val defaultRegion: String? = null,
    private val holidayFinder: HolidayFinder = HolidayFinderImpl(),
) {
    init {
        if (!isSupportedCountry(defaultCountryCode)) {
            throw IllegalArgumentException("Unsupported country code: $defaultCountryCode")
        }
    }

    /**
     * 영업일 및 공휴일 검색을 지원하는 나라인지 여부를 반환한다
     * @param countryCode 나라 코드 (e.g. "kor")
     * ref. https://kayaposoft.com/enrico/json/v3.0/openapi/#/Holiday%20Operations/getSupportedCountries
     * @return 지원 여부
     */
    fun isSupportedCountry(countryCode: String? = null): Boolean {
        return holidayFinder.isSupportedCountry(countryCode ?: defaultCountryCode)
    }

    /**
     * 특정 날짜에 대해 휴일 여부를 반환한다
     * @param date 날짜
     * @param countryCode 나라 코드 (e.g. "kor")
     * @param region 지역 코드
     * @return 휴일 여부
     */
    fun isHoliday(
        date: LocalDate,
        countryCode: String? = null,
        region: String? = null,
    ): Boolean {
        return this.isPublicHoliday(date, countryCode, region) || date.isWeekend()
    }

    /**
     * 특정 날짜에 대해 공휴일 여부를 반환한다
     * @param date 날짜
     * @param countryCode 나라 코드 (e.g. "kor")
     * @param region 지역 코드
     * @return 공휴일 여부
     */
    fun isPublicHoliday(
        date: LocalDate,
        countryCode: String? = null,
        region: String? = null,
    ): Boolean {
        countryCode?.let {
            return holidayFinder.isPublicHoliday(date, countryCode, region)
        } ?: run {
            return holidayFinder.isPublicHoliday(date, defaultCountryCode, defaultRegion)
        }
    }

    /**
     * 특정 연도의 날짜 목록을 반환한다
     * @param year 연도
     * @param countryCode 나라 코드 (e.g. "kor")
     * @param region 지역 코드
     * @return 날짜 목록
     */
    fun getDaysForYear(
        year: Year,
        countryCode: String? = null,
        region: String? = null,
    ): Days {
        val from = LocalDate.of(year.value, 1, 1)
        val to = LocalDate.of(year.value, 12, 31)
        val holidays = this.getPublicHolidayListForYear(year, countryCode, region)
        return this.separateDays(from, to, holidays, countryCode)
    }

    /**
     * 특정 연도의 공휴일 목록을 반환한다
     * @param year 연도
     * @param countryCode 나라 코드 (e.g. "kor")
     * @param region 지역 코드
     * @return 공휴일 목록
     */
    fun getPublicHolidaysForYear(
        year: Year,
        countryCode: String? = null,
        region: String? = null,
    ): Days {
        val holidays = this.getPublicHolidayListForYear(year, countryCode, region)
        return Days(holidays.map { it.toDay() })
    }

    /**
     * 특정 월의 날짜 목록을 반환한다
     * @param year 연도
     * @param month 월
     * @param countryCode 나라 코드 (e.g. "kor")
     * @param region 지역 코드
     * @return 날짜 목록
     */
    fun getDaysForMonth(
        year: Year,
        month: Month,
        countryCode: String? = null,
        region: String? = null,
    ): Days {
        val from = LocalDate.of(year.value, month.value, 1)
        val to = from.plusMonths(1).minusDays(1)
        val holidays = this.getPublicHolidayListForMonth(year, month, countryCode, region)
        return this.separateDays(from, to, holidays, countryCode)
    }

    /**
     * 특정 월의 공휴일 목록을 반환한다
     * @param year 연도
     * @param month 월
     * @param countryCode 나라 코드 (e.g. "kor")
     * @param region 지역 코드
     * @return 공휴일 목록
     */
    fun getPublicHolidaysForMonth(
        year: Year,
        month: Month,
        countryCode: String? = null,
        region: String? = null,
    ): Days {
        val holidays = this.getPublicHolidayListForMonth(year, month, countryCode, region)
        return Days(holidays.map { it.toDay() })
    }

    /**
     * 특정 날짜 범위의 날짜 목록을 반환한다
     * @param fromDate 시작 날짜
     * @param toDate 종료 날짜
     * @param countryCode 나라 코드 (e.g. "kor")
     * @param region 지역 코드
     * @return 날짜 목록
     */
    fun getDaysForDateRange(
        fromDate: LocalDate,
        toDate: LocalDate,
        countryCode: String? = null,
        region: String? = null,
    ): Days {
        val holidays = this.getPublicHolidayListForDateRange(fromDate, toDate, countryCode, region)
        return this.separateDays(fromDate, toDate, holidays, countryCode)
    }

    /**
     * 특정 날짜 범위의 공휴일 목록을 반환한다
     * @param fromDate 시작 날짜
     * @param toDate 종료 날짜
     * @param countryCode 나라 코드 (e.g. "kor")
     * @param region 지역 코드
     * @return 공휴일 목록
     */
    fun getHolidaysForDateRange(
        fromDate: LocalDate,
        toDate: LocalDate,
        countryCode: String? = null,
        region: String? = null,
    ): Days {
        val holidays = this.getPublicHolidayListForDateRange(fromDate, toDate, countryCode, region)
        return Days(holidays.map { it.toDay() })
    }

    private fun getPublicHolidayListForDateRange(
        fromDate: LocalDate,
        toDate: LocalDate,
        countryCode: String? = null,
        region: String? = null,
    ): List<Holiday> {
        return countryCode?.let {
            holidayFinder.getHolidaysForDateRange(fromDate, toDate, it, region)
        } ?: run {
            holidayFinder.getHolidaysForDateRange(fromDate, toDate, defaultCountryCode, defaultRegion)
        }
    }

    private fun separateDays(
        from: LocalDate,
        to: LocalDate,
        holidayList: List<Holiday>,
        countryCode: String?,
    ): Days {
        val code = countryCode ?: defaultCountryCode
        val holidays = holidayList.associateBy { it.date }

        val days = mutableListOf<Day>()
        val diff = ChronoUnit.DAYS.between(from, to).toInt()
        for (i in 0..diff) {
            val date = from.plusDays(i.toLong())
            if (holidays.contains(date)) {
                days.add(holidays[date]!!.toDay())
                continue
            }
            days.add(Day(date, code, false))
        }

        return Days(days)
    }

    private fun getPublicHolidayListForYear(
        year: Year,
        countryCode: String? = null,
        region: String? = null,
    ): List<Holiday> {
        return countryCode?.let {
            holidayFinder.getHolidaysForYear(year, it, region)
        } ?: run {
            holidayFinder.getHolidaysForYear(year, defaultCountryCode, defaultRegion)
        }
    }

    private fun getPublicHolidayListForMonth(
        year: Year,
        month: Month,
        countryCode: String? = null,
        region: String? = null,
    ): List<Holiday> {
        return countryCode?.let {
            holidayFinder.getHolidaysForMonth(year, month, it, region)
        } ?: run {
            holidayFinder.getHolidaysForMonth(year, month, defaultCountryCode, defaultRegion)
        }
    }
}
