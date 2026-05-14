package com.example.domain.analytic.usecases

import com.example.core.shareddomain.entities.CashFlow
import com.example.domain.analytic.repository.AnalyticRepository

class GetAnalyticDataUseCase(
    private val analyticRepository: AnalyticRepository
) {
    suspend operator fun invoke(year: Int, month: String? = null): Result<List<CashFlow>> {
        return analyticRepository.getCashFlows(year, month)
    }
}
