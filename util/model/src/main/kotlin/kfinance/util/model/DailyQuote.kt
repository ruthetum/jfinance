package kfinance.util.model

import java.time.LocalDate

data class DailyQuote(
    val date: LocalDate,
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Long,
    val adjustedClose: Double = -1.0,
) {
    fun price(): Double {
        if (adjustedClose == -1.0) {
            return close
        }
        return adjustedClose
    }
}
