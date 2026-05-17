package com.example.data.cashflow.datasource

import com.example.core.constans.enums.InputTransactionEnum
import com.example.core.shareddata.models.CashFlowModel
import com.example.data.cashflow.model.CashFlowPayloadModel
import com.example.data.cashflow.model.CashFlowResponse
import com.example.data.cashflow.model.PredictPricePayload
import com.example.data.cashflow.model.PredictPriceResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.functions.functions
import io.ktor.client.call.body
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.client.request.header
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

interface CashFlowNetworkDatasource {
    suspend fun createCashFlow(payload: CashFlowPayloadModel): Result<CashFlowResponse>
    suspend fun deleteCashFlow(id: String): Result<Unit>
    suspend fun checkAiPrice(category: String, productName: String, price: Double): Result<String>

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

    override suspend fun deleteCashFlow(id: String): Result<Unit> {
        return try {
            _supabaseClient.from("cash_flows").delete {
                filter {
                    eq("id", id)
                }
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkAiPrice(category: String, productName: String, price: Double): Result<String> {
        return try {
            val jsonBody = buildJsonObject {
                put("category", category)
                put("price", price)
                put("productName", productName)
            }
            val response = _supabaseClient.functions.invoke("predict_price") {
                header("Content-Type", "application/json")
                setBody(jsonBody.toString())
            }
            val jsonString = response.body<String>()
            val result = Json { ignoreUnknownKeys = true }.decodeFromString<PredictPriceResponse>(jsonString)
            Result.success(result.prediction)
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