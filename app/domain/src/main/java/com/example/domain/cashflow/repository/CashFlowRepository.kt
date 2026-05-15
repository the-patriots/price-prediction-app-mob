package com.example.domain.cashflow.repository

import com.example.core.constans.enums.InputTransactionEnum
import com.example.domain.cashflow.entities.CashFlowEntity
import com.example.domain.cashflow.entities.CashFlowPayload

interface CashFlowRepository {
    suspend fun createCashFlow(payload: CashFlowPayload): Result<String>
    suspend fun deleteCashFlow(id: String)
    suspend fun checkAiPrice(description: String, amount: Double): Result<String>

    suspend fun getCashflows(
        type: InputTransactionEnum.TypeCashFlow,
        month: String,
        year: Int,
        search: String = ""
    ): Result<List<CashFlowEntity>>
}