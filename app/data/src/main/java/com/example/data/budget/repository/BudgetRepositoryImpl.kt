package com.example.data.budget.repository

import com.example.data.budget.datasource.BudgetNetworkDataSource
import com.example.data.budget.models.BudgetModel
import com.example.domain.budget.entities.Budget
import com.example.domain.budget.repository.BudgetRepository

class BudgetRepositoryImpl(val datasource: BudgetNetworkDataSource) : BudgetRepository {
    override suspend fun getBudgets(): Result<List<Budget>> {
        try {
            val budgets = datasource.getBudgets().map {
                Budget(category = it.category, amount = it.amount, description = it.description)
            }

            return Result.success(budgets)

        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun createBudget(budget: Budget) {
        TODO("Not yet implemented")
    }
}