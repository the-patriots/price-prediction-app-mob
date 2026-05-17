package com.example.data.budget.datasource

import com.example.data.budget.models.BudgetModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

interface BudgetNetworkDataSource {
    suspend fun getBudgets(): List<BudgetModel>
}

class BudgetNetworkDataSourceImpl(private val client: SupabaseClient) : BudgetNetworkDataSource {
    companion object {
        const val TABLE_NAME = "budgets"
    }

    override suspend fun getBudgets(): List<BudgetModel> {
        try {
            return client.from(TABLE_NAME).select().decodeList<BudgetModel>()
        } catch (e: Exception) {
            throw e
        }
    }
}