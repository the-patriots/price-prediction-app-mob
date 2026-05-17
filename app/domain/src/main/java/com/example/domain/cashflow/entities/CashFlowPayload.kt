package com.example.domain.cashflow.entities

import com.example.core.shareddomain.entities.CashFlow

data class CashFlowPayload(
    val cashFlow: CashFlow
)

data class CashFlowEntity(
    val id: String,
    val type: String,
    val category: String,
    val description: String?,
    val amount: Double,
    val result: String? = null,
)