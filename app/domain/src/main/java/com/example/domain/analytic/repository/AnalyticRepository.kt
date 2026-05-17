package com.example.domain.analytic.repository

import com.example.core.shareddomain.entities.CashFlow

interface AnalyticRepository {
    suspend fun getCashFlows(year: Int, month: String? = null): Result<List<CashFlow>>
}
