package com.example.core.shareddata.models

import com.example.core.shareddomain.entities.CashFlow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CashFlowModel(
    @SerialName("id")
    val id: String?,
    @SerialName("type")
    val type: String,
    @SerialName("category")
    val category: String,
    @SerialName("amount")
    val amount: Double,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("month")
    val month: String,
    @SerialName("year")
    val year: Int,
    @SerialName("description")
    val description: String?
) {
    companion object {
        fun fromDomain(domain: CashFlow): CashFlowModel {
            return CashFlowModel(
                id = domain.id,
                type = domain.type,
                category = domain.category,
                amount = domain.amount,
                createdAt = domain.createdAt,
                month = domain.month,
                year = domain.year,
                description = domain.description
            )
        }
    }

    fun toDomain(): CashFlow {
        return CashFlow(
            id = this.id,
            type = this.type,
            category = this.category,
            amount = this.amount,
            createdAt = this.createdAt,
            month = this.month,
            year = this.year,
            description = this.description
        )
    }
}