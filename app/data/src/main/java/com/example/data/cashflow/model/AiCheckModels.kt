package com.example.data.cashflow.model

import kotlinx.serialization.Serializable

@Serializable
data class AiCheckPayload(val description: String, val amount: Double)

@Serializable
data class AiCheckResponse(val result: String)

