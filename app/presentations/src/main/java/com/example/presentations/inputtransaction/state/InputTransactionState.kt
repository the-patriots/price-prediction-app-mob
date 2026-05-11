package com.example.presentations.inputtransaction.state

data class InputTransactionState(
    val amount: String = "",
    val description: String = "",
    val category: String = "",
    val date: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
    val isFormValid: Boolean = false
)