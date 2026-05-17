package com.example.domain.budget.usecases

import com.example.domain.budget.entities.Budget
import com.example.domain.budget.repository.BudgetRepository

class GetBudgetsUseCase(private val repository: BudgetRepository) {
    suspend operator fun invoke(): Result<List<Budget>> {
        return repository.getBudgets()
    }
}