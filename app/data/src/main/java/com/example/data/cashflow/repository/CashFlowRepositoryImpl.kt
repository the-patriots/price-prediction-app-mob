package com.example.data.cashflow.repository

import com.example.core.constans.enums.InputTransactionEnum
import com.example.data.cashflow.datasource.CashFlowNetworkDatasource
import com.example.data.cashflow.model.CashFlowPayloadModel
import com.example.domain.cashflow.entities.AiCheck
import com.example.domain.cashflow.entities.CashFlowEntity
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

    override suspend fun deleteCashFlow(id: String): Result<Unit> {
        return cashFlowNetworkDatasource.deleteCashFlow(id)
    }

    override suspend fun checkAiPrice(category: String, productName: String, price: Double): Result<AiCheck> {
        return cashFlowNetworkDatasource.checkAiPrice(category, productName, price).map { it.toDomain() }
    }

    override suspend fun getCashflows(
        type: InputTransactionEnum.TypeCashFlow,
        month: String,
        year: Int,
        search: String
    ): Result<List<CashFlowEntity>> {
       try {
           val result = cashFlowNetworkDatasource.getCashflows(type, month, year, search)
           val list = result.getOrDefault(emptyList())
           return Result.success(list.map { CashFlowEntity(
               id = it.id.orEmpty(),
               type = it.type,
               category = it.category,
               description = it.description,
               amount = it.amount,
               result = it.result
           ) })
       }catch (e: Exception) {
           return Result.failure(e)
       }
    }
}