package com.example.domain.cashflow.entities

import com.example.core.shareddomain.entities.CashFlow
import java.io.File

data class CashFlowPayload(
    val image: File?,
    val cashFlow: CashFlow
)
