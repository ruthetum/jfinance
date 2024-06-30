package kfinance.util.calculator

import kotlin.math.pow
import kotlin.math.round

/**
 * 단리/복리 계산기
 */
object InterestCalculator {
    /**
     * 이자 계산 방법
     */
    enum class Method {
        /**
         * 단리
         */
        SIMPLE,

        /**
         * 월복리
         */
        MONTHLY_COMPOUND,
    }

    /**
     * 이자 계산 결과
     * @param principalAmount 원금
     * @param totalAmount 총액
     * @param earningsRate 수익률
     */
    data class Result(
        val principalAmount: Int,
        val totalAmount: Double,
        val earningsRate: Double,
    ) {
        override fun toString(): String {
            return "InterestCalculator.Result(principalAmount=$principalAmount, totalAmount=$totalAmount, earningsRate=$earningsRate)"
        }
    }

    /**
     * 이자 계산
     * @param depositAmount 예금액(만원)
     * @param depositPeriod 예금기간(개월)
     * @param interestRate 이자율
     * @param interestMethod 이자 계산 방법
     *
     * 이익 금액 계산 시 소숫점 이하 2자리까지 반올림하여 반환한다
     */
    fun calculate(
        depositAmount: Int,
        depositPeriod: Int,
        interestRate: Double,
        interestMethod: Method,
    ): Result {
        return when (interestMethod) {
            Method.SIMPLE -> calculateSimpleInterest(depositAmount, depositPeriod, interestRate)
            Method.MONTHLY_COMPOUND -> calculateCompoundInterest(depositAmount, depositPeriod, interestRate)
        }
    }

    private fun calculateSimpleInterest(depositAmount: Int, depositPeriod: Int, interestRate: Double): Result {
        val interest = depositAmount * depositPeriod * interestRate / 12 / 100
        val parsedInterest = round(interest * 100) / 100
        return Result(depositAmount, depositAmount + parsedInterest, parsedInterest / depositAmount * 100)
    }


    private fun calculateCompoundInterest(depositAmount: Int, depositPeriod: Int, interestRate: Double): Result {
        val totalAmount = depositAmount * (1 + interestRate / 12 / 100).pow(depositPeriod)
        val parsedTotalAmount = round(totalAmount * 100) / 100
        val interest = parsedTotalAmount - depositAmount
        val parsedEarningRate = round((interest / depositAmount * 100) * 100) / 100
        return Result(depositAmount, parsedTotalAmount, parsedEarningRate)
    }
}
