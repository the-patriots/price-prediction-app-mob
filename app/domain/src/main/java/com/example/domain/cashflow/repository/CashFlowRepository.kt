package com.example.domain.cashflow.repository

import com.example.core.shareddomain.entities.CashFlow
import com.example.domain.cashflow.entities.CashFlowPayload

interface CashFlowRepository {
    suspend fun createCashFlow(payload: CashFlowPayload): Result<String>
    suspend fun deleteCashFlow(id: String)
    suspend fun updateCashFlow(payload: CashFlowPayload)
}