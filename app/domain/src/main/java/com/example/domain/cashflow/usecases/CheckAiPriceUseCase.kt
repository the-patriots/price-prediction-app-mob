package com.example.domain.cashflow.usecases

import com.example.domain.cashflow.repository.CashFlowRepository

class CheckAiPriceUseCase(
    private val cashFlowRepository: CashFlowRepository
) {
    suspend operator fun invoke(description: String, amount: Double): Result<String> {
        return cashFlowRepository.checkAiPrice(description, amount)
    }
}
