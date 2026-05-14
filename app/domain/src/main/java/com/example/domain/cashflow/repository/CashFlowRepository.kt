package com.example.domain.cashflow.repository

import com.example.domain.cashflow.entities.CashFlowPayload

interface CashFlowRepository {
    suspend fun createCashFlow(payload: CashFlowPayload): Result<String>
    suspend fun deleteCashFlow(id: String)
    suspend fun checkAiPrice(description: String, amount: Double): Result<String>
}