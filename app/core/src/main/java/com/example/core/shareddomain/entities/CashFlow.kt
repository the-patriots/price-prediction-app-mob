package com.example.core.shareddomain.entities

data class CashFlow(
    val id: String?,
    val type: String,
    val category: String,
    val amount: Double,
    val createdAt: String?,
    val month: String,
    val year: Int,
    val description: String?
)