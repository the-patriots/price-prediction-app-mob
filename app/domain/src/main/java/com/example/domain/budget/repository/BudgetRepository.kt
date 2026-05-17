package com.example.domain.budget.repository

import com.example.domain.budget.entities.Budget

interface BudgetRepository {
    suspend fun getBudgets(): Result<List<Budget>>
   suspend fun createBudget(budget: Budget)
}