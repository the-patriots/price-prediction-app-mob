package com.example.domain.cashflow.usecases

import com.example.domain.cashflow.entities.CashFlowPayload
import com.example.domain.cashflow.repository.CashFlowRepository

class EditCashflowUseCase(private val repository: CashFlowRepository) {
    suspend operator fun invoke(id: String, payload: CashFlowPayload): Result<String> {
        return repository.editCashFlow(id, payload)
    }
}