package com.example.data.analytic.datasource

import android.util.Log
import com.example.core.shareddata.models.CashFlowModel
import com.example.core.shareddomain.entities.CashFlow
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest

interface AnalyticNetworkDatasource {
    suspend fun getCashFlows(year: Int, month: String?): Result<List<CashFlow>>
}

class AnalyticNetworkDatasourceImpl(
    private val supabaseClient: SupabaseClient
) : AnalyticNetworkDatasource {

    override suspend fun getCashFlows(year: Int, month: String?): Result<List<CashFlow>> {
        return try {
            val userID =  supabaseClient.auth.sessionManager.loadSession()?.let {
                it.user?.id
            }
            if (userID.isNullOrBlank()) throw (Exception("no session found"))
            val result = supabaseClient.postgrest["cash_flows"]
                .select {
                    filter {
                        eq("user_id",userID)
                        eq("year", year)
                        if (month != null) {
                            eq("month", month)
                        }
                    }
                }
                .decodeList<CashFlowModel>()
                .map { it.toDomain() }
            Log.d("AnalyticNetworkDatasourceImpl", "Fetched cash flows: $result")

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
