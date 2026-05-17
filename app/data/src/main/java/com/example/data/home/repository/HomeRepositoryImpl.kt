package com.example.data.home.repository

import com.example.core.shareddomain.entities.CashFlow
import com.example.data.home.datasource.HomeNetworkDatasource
import com.example.domain.home.entities.HomeSummary
import com.example.domain.home.repository.HomeRepository

class HomeRepositoryImpl(
    private val homeNetworkDatasource: HomeNetworkDatasource
) : HomeRepository {

    override suspend fun getHomeSummary(month: String?, year: Int): Result<HomeSummary> {
        return homeNetworkDatasource.getHomeSummary(month = month, year = year)
    }

    override suspend fun getRecentCashFlows(month: String?, year: Int): Result<List<CashFlow>> {
        return homeNetworkDatasource.getRecentCashFlows(month = month, year = year)
    }
}
