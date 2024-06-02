package kfinance.util.model

import java.time.LocalDateTime

data class Quote(
    val dateTime: LocalDateTime,
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
