package kfinance.stock.yahoo

import kfinance.util.converter.toTimestamp
import kfinance.util.model.DailyQuote
import tech.tablesaw.api.Table
import java.net.URL
import java.time.LocalDate

class YahooDataReader {
    /**
     * 특정 종목의 주가(조정된 종가)를 리스트 형태로 반환한다
     * @param symbol 종목 심볼
     * @param startDate 조회 시작일
     * @param endDate 조회 종료일
     * @param interval 조회 간격
     */
    fun getPrice(
        symbol: String,
        startDate: String = LocalDate.now().minusYears(1).toString(),
        endDate: String = LocalDate.now().toString(),
        interval: String = "1d",
    ): List<Pair<LocalDate, Double>> {
        val ohlcv = this.getPriceWithTable(symbol, startDate, endDate, interval)
        return ohlcv.asSequence().map {
            Pair(
                it.getDate(YahooDataOhlcvColumn.DATE.columnName),
                it.getDouble(1),
            )
        }.toList()
    }

    /**
     * 특정 종목의 주가(조정된 종가)를 테이블 형태로 반환한다
     * @param symbol 종목 심볼
     * @param startDate 조회 시작일
     * @param endDate 조회 종료일
     * @param interval 조회 간격
     */
    fun getPriceWithTable(
        symbol: String,
        startDate: String = LocalDate.now().minusYears(1).toString(),
        endDate: String = LocalDate.now().toString(),
        interval: String = "1d",
    ): Table {
        val ohlcv = this.getOhlcvWithTable(symbol, startDate, endDate, interval)
        return ohlcv.selectColumns(YahooDataOhlcvColumn.DATE.columnName, YahooDataOhlcvColumn.ADJ_CLOSE.columnName)
    }

    /**
     * 특정 종목의 시가/고가/저가/종가/거래량을 리스트 형태로 반환한다
     * @param symbol 종목 심볼
     * @param startDate 조회 시작일
     * @param endDate 조회 종료일
     * @param interval 조회 간격
     */
    fun getOhlcv(
        symbol: String,
        startDate: String = LocalDate.now().minusYears(1).toString(),
        endDate: String = LocalDate.now().toString(),
        interval: String = "1d",
    ): List<DailyQuote> {
        val ohlcv = this.getOhlcvWithTable(symbol, startDate, endDate, interval)
        return ohlcv.asSequence().map {
            DailyQuote(
                it.getDate(YahooDataOhlcvColumn.DATE.columnName),
                it.getDouble(YahooDataOhlcvColumn.OPEN.columnName),
                it.getDouble(YahooDataOhlcvColumn.HIGH.columnName),
                it.getDouble(YahooDataOhlcvColumn.LOW.columnName),
                it.getDouble(YahooDataOhlcvColumn.CLOSE.columnName),
                it.getInt(YahooDataOhlcvColumn.VOLUME.columnName).toLong(),
                it.getDouble(YahooDataOhlcvColumn.ADJ_CLOSE.columnName),
            )
        }.toList()
    }

    /**
     * 특정 종목의 시가/고가/저가/종가/거래량을 테이블 형태로 반환한다
     * @param symbol 종목 심볼
     * @param startDate 조회 시작일
     * @param endDate 조회 종료일
     * @param interval 조회 간격
     */
    fun getOhlcvWithTable(
        symbol: String,
        startDate: String = LocalDate.now().minusYears(1).toString(),
        endDate: String = LocalDate.now().toString(),
        interval: String = "1d",
    ): Table {
        val url =
            this.getOhlcvDownloadUrl(
                symbol,
                LocalDate.parse(startDate),
                LocalDate.parse(endDate),
                YahooDataInterval.of(interval),
            )
        return Table.read().csv(URL(url))
    }

    private fun getOhlcvDownloadUrl(
        symbol: String,
        startDate: LocalDate,
        endDate: LocalDate,
        interval: YahooDataInterval,
    ): String {
        return "$OHLCV_DOWNLOAD$symbol?period1=${startDate.toTimestamp()}" +
            "&period2=${endDate.toTimestamp()}&interval=${interval.desc}&events=history"
    }

    companion object {
        private const val API_HOST = "https://query1.finance.yahoo.com"
        private const val OHLCV_DOWNLOAD = "${API_HOST}/v7/finance/download/"
    }
}
