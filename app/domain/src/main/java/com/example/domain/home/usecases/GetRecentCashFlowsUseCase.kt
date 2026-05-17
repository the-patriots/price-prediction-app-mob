package com.example.domain.home.usecases

import com.example.core.shareddomain.entities.CashFlow
import com.example.domain.home.repository.HomeRepository

class GetRecentCashFlowsUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(month: String?, year: Int): Result<List<CashFlow>> {
        return repository.getRecentCashFlows(month = month, year = year)
    }
}
