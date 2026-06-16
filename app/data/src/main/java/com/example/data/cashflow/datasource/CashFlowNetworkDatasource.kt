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
import io.github.jan.supabase.postgrest.query.Order
import io.ktor.client.statement.bodyAsText
import io.ktor.client.request.setBody
import io.ktor.client.request.header

interface CashFlowNetworkDatasource {
    suspend fun createCashFlow(payload: CashFlowPayloadModel): Result<CashFlowResponse>

    suspend fun editCashFlow(id: String, payload: CashFlowPayloadModel): Result<CashFlowResponse>
    suspend fun deleteCashFlow(id: String): Result<Unit>
    suspend fun checkAiPrice(
        category: String,
        productName: String,
        price: Double
    ): Result<PredictPriceResponse>

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
            val userID = _supabaseClient.auth.sessionManager.loadSession()?.let {
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

    override suspend fun editCashFlow(
        id: String,
        payload: CashFlowPayloadModel
    ): Result<CashFlowResponse> {
        return try {

            _supabaseClient.from("cash_flows").update({
                set("category", payload.category)
                set("amount", payload.amount)
                set("month", payload.month)
                set("year", payload.year)
                set("result", payload.result)
                set("description", payload.description)
            }) {
                filter {
                    eq("id", id)
                }
                select()
            }

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

            _supabaseClient.from("users")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkAiPrice(
        category: String,
        productName: String,
        price: Double
    ): Result<PredictPriceResponse> {
        return try {
            val payload = PredictPricePayload(
                category = category,
                price = price,
                productName = productName
            )
            val response = _supabaseClient.functions.invoke("predict_price") {
                header("Content-Type", "application/json")
                setBody(payload)
            }
            val rawBody = response.bodyAsText()
            println("[CashFlowNetworkDatasource] predict_price raw response: $rawBody")
            val result = PredictPriceResponse.fromRawJson(rawBody)
            Result.success(result)
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
            val userID = _supabaseClient.auth.sessionManager.loadSession()?.let {
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

                order(column = "created_at", order = Order.DESCENDING)
            }.decodeList<CashFlowModel>()

            return Result.success(result)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}