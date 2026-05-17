package com.example.data.cashflow.model

import kotlinx.serialization.SerialName
import com.example.domain.cashflow.entities.CashFlowPayload as DomainCashFlowPayload
import kotlinx.serialization.Serializable

@Serializable
data class CashFlowPayloadModel(
    @SerialName("user_id")
    var userId: String,
    val type: String,
    val category: String,
    val amount: Double,
    val month: String,
    val year: Int,
    val description: String,
    val result: String? = null,
) {
    companion object {
        fun fromDomain(payload: DomainCashFlowPayload): CashFlowPayloadModel {
            return CashFlowPayloadModel(
                type = payload.cashFlow.type,
                category = payload.cashFlow.category,
                amount = payload.cashFlow.amount,
                month = payload.cashFlow.month,
                year = payload.cashFlow.year,
                description = payload.cashFlow.description ?: "",
                result = payload.cashFlow.result,
                userId = "",
            )
        }
    }
}