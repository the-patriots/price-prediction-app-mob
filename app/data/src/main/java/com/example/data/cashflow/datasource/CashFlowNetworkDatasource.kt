package com.example.data.cashflow.datasource

import com.example.data.cashflow.model.CashFlowPayloadModel
import com.example.data.cashflow.model.CashFlowResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.functions.functions
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import com.example.data.cashflow.model.AiCheckPayload
import com.example.data.cashflow.model.AiCheckResponse
import kotlinx.coroutines.delay

interface CashFlowNetworkDatasource {
    suspend fun createCashFlow(payload: CashFlowPayloadModel): Result<CashFlowResponse>
    suspend fun deleteCashFlow(id: String)
    suspend fun checkAiPrice(description: String, amount: Double): Result<String>
}

class CashFLowNetworkDatasourceImpl(
    private val _supabaseClient: SupabaseClient,
): CashFlowNetworkDatasource {
    override suspend fun createCashFlow(payload: CashFlowPayloadModel): Result<CashFlowResponse> {
        return try {
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
}