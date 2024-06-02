package kfinance.stock.yahoo

enum class YahooDataOhlcvColumn(val columnName: String, val order: Int) {
    DATE("Date", 0),
    OPEN("Open", 1),
    HIGH("High", 2),
    LOW("Low", 3),
    CLOSE("Close", 4),
    ADJ_CLOSE("Adj Close", 5),
    VOLUME("Volume", 6),
    ;

    companion object {
        fun names(): List<String> {
            return values().sortedBy { it.order }.map { it.columnName }
        }
    }
}
