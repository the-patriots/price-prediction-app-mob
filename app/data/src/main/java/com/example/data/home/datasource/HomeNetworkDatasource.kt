package com.example.data.home.datasource

import android.util.Log
import com.example.core.shareddata.models.CashFlowModel
import com.example.core.shareddomain.entities.CashFlow
import com.example.domain.home.entities.HomeSummary
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Order

interface HomeNetworkDatasource {
    suspend fun getHomeSummary(month: String?, year: Int): Result<HomeSummary>
    suspend fun getRecentCashFlows(month: String?, year: Int): Result<List<CashFlow>>
}

class HomeNetworkDatasourceImpl(
    private val supabaseClient: SupabaseClient
) : HomeNetworkDatasource {

    private suspend fun getUserId(): String {
        val userID = supabaseClient.auth.sessionManager.loadSession()?.let {
            it.user?.id
        }
        if (userID.isNullOrBlank()) throw Exception("no session found")
        return userID
    }

    override suspend fun getHomeSummary(month: String?, year: Int): Result<HomeSummary> {
        return try {
            val userId = getUserId()

            val cashFlows = supabaseClient.from("cash_flows").select {
                filter {
                    eq("user_id", userId)
                    eq("year", year)
                    if (month != null) {
                        eq("month", month)
                    }
                }
            }.decodeList<CashFlowModel>()

            val totalIncome = cashFlows
                .filter { it.type == "Pemasukan" }
                .sumOf { it.amount }

            val totalExpense = cashFlows
                .filter { it.type == "Pengeluaran" }
                .sumOf { it.amount }

            Log.d("HomeNetworkDatasource", "Income: $totalIncome, Expense: $totalExpense")

            Result.success(HomeSummary(totalIncome = totalIncome, totalExpense = totalExpense))
        } catch (e: Exception) {
            Log.e("HomeNetworkDatasource", "Error fetching home summary", e)
            Result.failure(e)
        }
    }

    override suspend fun getRecentCashFlows(month: String?, year: Int): Result<List<CashFlow>> {
        return try {
            val userId = getUserId()

            val result = supabaseClient.from("cash_flows").select {
                filter {
                    eq("user_id", userId)
                    eq("year", year)
                    if (month != null) {
                        eq("month", month)
                    }
                }
                order("created_at", Order.DESCENDING)
            }.decodeList<CashFlowModel>()
                .map { it.toDomain() }

            Log.d("HomeNetworkDatasource", "Fetched ${result.size} recent cash flows")

            Result.success(result)
        } catch (e: Exception) {
            Log.e("HomeNetworkDatasource", "Error fetching recent cash flows", e)
            Result.failure(e)
        }
    }
}
