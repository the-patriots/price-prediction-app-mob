package com.example.data.cashflow.datasource

import com.example.data.cashflow.model.CashFlowPayloadModel
import com.example.data.cashflow.model.CashFlowResponse
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.functions.functions
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

interface CashFlowNetworkDatasource {
    suspend fun createCashFlow(payload: CashFlowPayloadModel): Result<CashFlowResponse>
    suspend fun updateCashFLow(payload: CashFlowPayloadModel)
    suspend fun deleteCashFlow(id: String)
}

class CashFLowNetworkDatasourceImpl(
    private val _supabaseClient: SupabaseClient,
): CashFlowNetworkDatasource {
    override suspend fun createCashFlow(payload: CashFlowPayloadModel): Result<CashFlowResponse> {
        return try {
            val response = _supabaseClient.functions.invoke("upload_cash_flow") {
                setBody(MultiPartFormDataContent(payload.toMultipartBody()))
            }
            
            val jsonString = response.bodyAsText()
            val result = Json { ignoreUnknownKeys = true }.decodeFromString<CashFlowResponse>(jsonString)
            
            if (result.error != null) {
                Result.failure(Exception(result.error))
            } else {
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateCashFLow(payload: CashFlowPayloadModel) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCashFlow(id: String) {
        TODO("Not yet implemented")
    }
}