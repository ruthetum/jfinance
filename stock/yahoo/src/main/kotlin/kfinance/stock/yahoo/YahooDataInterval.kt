package kfinance.stock.yahoo

enum class YahooDataInterval(val desc: String) {
    ONE_DAY("1d"),
    FIVE_DAYS("5d"),
    ONE_WEEK("1wk"),
    ONE_MONTH("1mo"),
    THREE_MONTHS("3mo"),
    ;

    companion object {
        fun of(interval: String): YahooDataInterval {
            return YahooDataInterval.values().firstOrNull { it.desc == interval }
                ?: throw IllegalArgumentException("Invalid interval: $interval")
        }
    }
}
