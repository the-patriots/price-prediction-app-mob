package com.example.domain.home.repository

import com.example.core.shareddomain.entities.CashFlow
import com.example.domain.home.entities.HomeSummary

interface HomeRepository {
    suspend fun getHomeSummary(month: String?, year: Int): Result<HomeSummary>
    suspend fun getRecentCashFlows(month: String?, year: Int): Result<List<CashFlow>>
}
