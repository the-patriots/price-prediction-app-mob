package com.example.domain.budget.repository

import com.example.domain.budget.entities.Budget

interface BudgetRepository {
    fun getBudgets(): List<Budget>
    fun createBudget(budget: Budget)
}