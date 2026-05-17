package com.example.data.budget.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.budget.models.BudgetModel

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budgets")
    fun getBudgets()

    @Query("SELECT * FROM budgets WHERE year = :year")
    fun findByYear(year: Int)

    @Insert
    fun insertBudget(budget: BudgetModel)
}