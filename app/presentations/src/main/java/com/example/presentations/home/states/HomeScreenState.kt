package com.example.presentations.home.states

import com.example.domain.budget.entities.Budget

data class HomeScreenState(
    val expand: Boolean = false,
    val balance: Int = 0,
    val income: Int = 0,
    val outcome: Int = 0,
    val budgets: List<Budget> = emptyList(),
    var month: String = "Januari"
)
