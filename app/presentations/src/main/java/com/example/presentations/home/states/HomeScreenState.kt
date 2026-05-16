package com.example.presentations.home.states

import com.example.core.shareddomain.entities.CashFlow

data class HomeScreenState(
    val expand: Boolean = false,
    val balance: Double = 0.0,
    val income: Double = 0.0,
    val outcome: Double = 0.0,
    val recentCashFlows: List<CashFlow> = emptyList(),
    var month: String = "Semua Bulan",
    val year: Int = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),
    val isLoading: Boolean = false
)
