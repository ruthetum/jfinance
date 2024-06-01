package kfinance.common.calendar.model

data class Days(
    val days: List<Day>,
) {
    init {
        days.sortedBy { it.date }
    }

    fun getBusinessDays(): List<Day> {
        return days.filter { !it.isHoliday() }
    }

    fun getHolidays(): List<Day> {
        return days.filter { it.isHoliday() }
    }
}
