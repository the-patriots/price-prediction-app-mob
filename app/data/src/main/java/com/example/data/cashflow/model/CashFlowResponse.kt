package com.example.data.cashflow.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CashFlowResponse(
    @SerialName("message")
    val message: String? = null,
    @SerialName("error")
    val error: String? = null
)