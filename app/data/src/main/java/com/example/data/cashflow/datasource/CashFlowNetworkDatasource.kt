package com.example.data.cashflow.datasource

import com.example.core.constans.enums.InputTransactionEnum
import com.example.core.shareddata.models.CashFlowModel
import com.example.data.cashflow.model.CashFlowPayloadModel
import com.example.data.cashflow.model.CashFlowResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.delay

interface CashFlowNetworkDatasource {
    suspend fun createCashFlow(payload: CashFlowPayloadModel): Result<CashFlowResponse>
    suspend fun deleteCashFlow(id: String)
    suspend fun checkAiPrice(description: String, amount: Double): Result<String>

    suspend fun getCashflows(
        type: InputTransactionEnum.TypeCashFlow,
        month: String,
        year: Int,
        search: String = ""
    ): Result<List<CashFlowModel>>
}

class CashFLowNetworkDatasourceImpl(
    private val _supabaseClient: SupabaseClient,
) : CashFlowNetworkDatasource {
    override suspend fun createCashFlow(payload: CashFlowPayloadModel): Result<CashFlowResponse> {
        return try {
            val userID =  _supabaseClient.auth.sessionManager.loadSession()?.let {
                it.user?.id
            }
            if (userID.isNullOrBlank()) throw (Exception("no session found"))
            payload.userId = userID
            _supabaseClient.postgrest["cash_flows"].insert(payload)
            Result.success(CashFlowResponse(message = "Success", error = null))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteCashFlow(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun checkAiPrice(description: String, amount: Double): Result<String> {
        return try {
            delay(2000)
//            val response = _supabaseClient.functions.invoke("check_ai_price") {
//                setBody(AiCheckPayload(description, amount))
//            }
//            val jsonString = response.bodyAsText()
//            val result = Json { ignoreUnknownKeys = true }.decodeFromString<AiCheckResponse>(jsonString)
            Result.success("sukses")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCashflows(
        type: InputTransactionEnum.TypeCashFlow,
        month: String,
        year: Int,
        search: String
    ): Result<List<CashFlowModel>> {
        try {
           val userID =  _supabaseClient.auth.sessionManager.loadSession()?.let {
                it.user?.id
            }

            if (userID.isNullOrBlank()) throw (Exception("no session found"))

            val result = _supabaseClient.from("cash_flows").select {
                filter {
                    eq("user_id", userID)
                    if (month.isNotBlank()) {
                        eq("month", month)
                    }
                    eq("year", year)
                    eq("type", type.value)
                    if (search.isNotBlank()) {
                        or {
                            ilike("category", "%$search%")
                            ilike("description", "%$search%")
                        }
                    }
                }
            }.decodeList<CashFlowModel>()

            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }


}