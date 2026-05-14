package com.example.data.analytic.repository

import com.example.core.shareddomain.entities.CashFlow
import com.example.data.analytic.datasource.AnalyticNetworkDatasource
import com.example.domain.analytic.repository.AnalyticRepository

class AnalyticRepositoryImpl(
    private val analyticNetworkDatasource: AnalyticNetworkDatasource
) : AnalyticRepository {

    override suspend fun getCashFlows(year: Int, month: String?): Result<List<CashFlow>> {
        return analyticNetworkDatasource.getCashFlows(year, month)
    }
}
