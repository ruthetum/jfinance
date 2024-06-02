package kfinance.stock.yahoo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import tech.tablesaw.api.Row
import java.time.LocalDate

class YahooDataReaderTest {
    private val reader = YahooDataReader()

    @DisplayName("특정 종목의 주가(조정된 종가)를 리스트 형태로 조회한다")
    @Test
    fun priceShouldBeReturned() {
        // given
        val symbol = "AAPL"
        val startDate = "2023-06-01"
        val endDate = "2023-06-30"

        // when
        val prices = reader.getPrice(symbol, startDate, endDate)

        // then
        assertEquals(
            21,
            prices.size,
        )

        val firstPrice = prices.first()
        assertEquals(
            LocalDate.parse("2023-05-31"),
            firstPrice.first,
        )
        assertEquals(
            176.31366,
            firstPrice.second,
        )
    }

    @DisplayName("특정 종목의 주가(조정된 종가)를 테이블 형태로 조회한다")
    @Test
    fun priceWithTableShouldBeReturned() {
        // given
        val symbol = "AAPL"
        val startDate = "2023-06-01"
        val endDate = "2023-06-30"

        // when
        val table = reader.getPriceWithTable(symbol, startDate, endDate)

        // then
        assertEquals(
            listOf(YahooDataOhlcvColumn.DATE.columnName, YahooDataOhlcvColumn.ADJ_CLOSE.columnName),
            table.columnNames(),
        )
        assertEquals(
            21,
            table.rowCount(),
        )

        val firstRow: Row = table.firstOrNull()!!
        assertEquals(
            LocalDate.parse("2023-05-31"),
            firstRow.getDate(YahooDataOhlcvColumn.DATE.columnName),
        )
        assertEquals(
            176.31366,
            firstRow.getDouble(YahooDataOhlcvColumn.ADJ_CLOSE.columnName),
        )
    }

    @DisplayName("특정 종목의 시가/고가/저가/종가/거래량을 리스트 형태로 조회한다")
    @Test
    fun ohlcvShouldBeReturned() {
        // given
        val symbol = "AAPL"
        val startDate = "2023-06-01"
        val endDate = "2023-06-30"

        // when
        val ohlcv = reader.getOhlcv(symbol, startDate, endDate)

        // then
        assertEquals(
            21,
            ohlcv.size,
        )

        val firstQuote = ohlcv.first()
        assertEquals(
            LocalDate.parse("2023-05-31"),
            firstQuote.date,
        )
        assertEquals(
            177.330002,
            firstQuote.open,
        )
        assertEquals(
            99625300,
            firstQuote.volume,
        )
    }

    @DisplayName("특정 종목의 시가/고가/저가/종가/거래량을 테이블 형태로 조회한다")
    @Test
    fun ohlcvWithTableShouldBeReturned() {
        // given
        val symbol = "AAPL"
        val startDate = "2023-06-01"
        val endDate = "2023-06-30"

        // when
        val table = reader.getOhlcvWithTable(symbol, startDate, endDate)

        // then
        assertEquals(
            YahooDataOhlcvColumn.names(),
            table.columnNames(),
        )
        assertEquals(
            21,
            table.rowCount(),
        )

        val firstRow: Row = table.firstOrNull()!!
        assertEquals(
            LocalDate.parse("2023-05-31"),
            firstRow.getDate(YahooDataOhlcvColumn.DATE.columnName),
        )
        assertEquals(
            177.330002,
            firstRow.getDouble(YahooDataOhlcvColumn.OPEN.columnName),
        )
        assertEquals(
            99625300,
            firstRow.getInt(YahooDataOhlcvColumn.ADJ_CLOSE.columnName),
        )
    }
}
