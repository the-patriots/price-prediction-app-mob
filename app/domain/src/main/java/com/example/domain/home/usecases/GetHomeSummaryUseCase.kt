package com.example.domain.home.usecases

import com.example.domain.home.entities.HomeSummary
import com.example.domain.home.repository.HomeRepository

class GetHomeSummaryUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(month: String?, year: Int): Result<HomeSummary> {
        return repository.getHomeSummary(month = month, year = year)
    }
}
