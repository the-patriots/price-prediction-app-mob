package com.example.domain.cashflow.usecases

import com.example.domain.cashflow.repository.CashFlowRepository

class DeleteCashFlowUseCase(
    private val cashFlowRepository: CashFlowRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return cashFlowRepository.deleteCashFlow(id)
    }
}
