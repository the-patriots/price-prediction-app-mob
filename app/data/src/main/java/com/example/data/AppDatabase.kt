package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.budget.datasource.BudgetDao
import com.example.data.budget.models.Budget

@Database(entities = [Budget::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
}