package kfinance.stock.yahoo

enum class YahooDataDividendsColumn(val columnName: String, val order: Int) {
    DATE("Date", 0),
    DIVIDENDS("Dividends", 1),
    ;

    companion object {
        fun names(): List<String> {
            return values().sortedBy { it.order }.map { it.columnName }
        }
    }
}
