package kfinance.util.converter

import java.time.LocalDate
import java.time.ZoneId

class LocalDateConverter

fun LocalDate.toTimestamp(): Long {
    return this.atStartOfDay(ZoneId.systemDefault())
        .toInstant().toEpochMilli() / 1000
}

fun LocalDate.toNanoTimestamp(): Long {
    return this.atStartOfDay(ZoneId.systemDefault())
        .toInstant().toEpochMilli()
}
