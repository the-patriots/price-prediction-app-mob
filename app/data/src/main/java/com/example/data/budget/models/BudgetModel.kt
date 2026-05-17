package com.example.data.budget.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BudgetModel(
    @SerialName("id")
    val id: String,
    @SerialName("category")
    val category: String,
    @SerialName("description")
    val description: String,
    @SerialName("amount")
    val amount: Int,
    @SerialName("month")
    val month: String,
    @SerialName("year")
    val year: Int,
)