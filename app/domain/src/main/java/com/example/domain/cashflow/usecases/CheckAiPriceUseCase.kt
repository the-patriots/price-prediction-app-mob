package com.example.domain.cashflow.usecases

import com.example.domain.cashflow.entities.AiCheck
import com.example.domain.cashflow.repository.CashFlowRepository

class CheckAiPriceUseCase(
    private val cashFlowRepository: CashFlowRepository
) {
    suspend operator fun invoke(category: String, productName: String, price: Double): Result<AiCheck> {
        return cashFlowRepository.checkAiPrice(category, productName, price)
    }
}
