package com.example.data.cashflow.model

import kotlinx.serialization.Serializable

@Serializable
data class PredictPricePayload(
    val category: String,
    val price: Double,
    val productName: String
)

@Serializable
data class PredictPriceResponse(val prediction: String)
