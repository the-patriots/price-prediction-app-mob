package com.example.domain.cashflow.usecases

import com.example.domain.cashflow.entities.CashFlowPayload
import com.example.domain.cashflow.repository.CashFlowRepository

class CreateCashFlowUseCase(
    private val cashFlowRepository: CashFlowRepository
) {
    suspend operator fun invoke(payload: CashFlowPayload): Result<String> {
        return cashFlowRepository.createCashFlow(payload)
    }
}

