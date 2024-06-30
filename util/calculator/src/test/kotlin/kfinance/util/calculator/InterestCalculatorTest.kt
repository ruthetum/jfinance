package kfinance.util.calculator

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class InterestCalculatorTest {
    @DisplayName("100만원을 12개월간 10% 이자율로 단리로 계산하면 10만원의 이자가 발생한다")
    @Test
    fun calculateSimpleInterest() {
        // given
        val depositAmount = 100
        val depositPeriod = 12
        val interestRate = 10.0

        // when
        val result = InterestCalculator.calculate(
            depositAmount,
            depositPeriod,
            interestRate,
            InterestCalculator.Method.SIMPLE
        )

        // then
        assert(result.totalAmount == 110.0)
        assert(result.earningsRate == interestRate)
    }

    @DisplayName("100만원을 12개월간 10% 이자율로 월복리로 계산하면 10.47만원의 이자가 발생한다")
    @Test
    fun calculateCompoundInterest() {
        // given
        val depositAmount = 100
        val depositPeriod = 12
        val interestRate = 10.0

        // when
        val result = InterestCalculator.calculate(
            depositAmount,
            depositPeriod,
            interestRate,
            InterestCalculator.Method.MONTHLY_COMPOUND
        )

        // then
        assert(result.totalAmount == 110.47)
        assert(result.earningsRate == 10.47)
    }
}
