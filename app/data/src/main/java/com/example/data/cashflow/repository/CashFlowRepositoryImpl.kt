package com.example.data.cashflow.repository

import com.example.data.cashflow.datasource.CashFlowNetworkDatasource
import com.example.data.cashflow.model.CashFlowPayloadModel
import com.example.domain.cashflow.entities.CashFlowPayload
import com.example.domain.cashflow.repository.CashFlowRepository

class CashFlowRepositoryImpl(
    private val cashFlowNetworkDatasource: CashFlowNetworkDatasource
) : CashFlowRepository {
    override suspend fun createCashFlow(payload: CashFlowPayload): Result<String> {
        val dataModel = CashFlowPayloadModel.fromDomain(payload)
        val response = cashFlowNetworkDatasource.createCashFlow(dataModel)
        return response.map { it.message ?: "Success" }
    }

    override suspend fun deleteCashFlow(id: String) {
        cashFlowNetworkDatasource.deleteCashFlow(id)
    }

    override suspend fun checkAiPrice(description: String, amount: Double): Result<String> {
        return cashFlowNetworkDatasource.checkAiPrice(description, amount)
    }
}