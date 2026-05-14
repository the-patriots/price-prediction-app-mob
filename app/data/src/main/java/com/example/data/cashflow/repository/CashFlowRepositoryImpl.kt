package com.example.data.cashflow.repository

import com.example.core.shareddomain.entities.CashFlow
import com.example.domain.cashflow.entities.CashFlowPayload
import com.example.data.cashflow.model.CashFlowPayloadModel
import com.example.domain.cashflow.repository.CashFlowRepository
import com.example.data.cashflow.datasource.CashFlowNetworkDatasource

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

    override suspend fun updateCashFlow(payload: CashFlowPayload) {
        val dataModel = CashFlowPayloadModel.fromDomain(payload)
        cashFlowNetworkDatasource.updateCashFLow(dataModel)
    }
}