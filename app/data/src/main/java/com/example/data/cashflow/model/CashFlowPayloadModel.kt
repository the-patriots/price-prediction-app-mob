package com.example.data.cashflow.model

import com.example.domain.cashflow.entities.CashFlowPayload as DomainCashFlowPayload
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData

data class CashFlowPayloadModel(
    val type: String,
    val category: String,
    val amount: String,
    val month: String,
    val year: Int,
    val description: String,
    val imageBytes: ByteArray
) {
    companion object {
        fun fromDomain(payload: DomainCashFlowPayload): CashFlowPayloadModel {
            return CashFlowPayloadModel(
                type = payload.cashFlow.type,
                category = payload.cashFlow.category,
                amount = payload.cashFlow.amount.toString(),
                month = payload.cashFlow.month,
                year = payload.cashFlow.year,
                description = payload.cashFlow.description ?: "",
                imageBytes = payload.image?.readBytes() ?: ByteArray(0)
            )
        }
    }

    fun toMultipartBody(): List<PartData> = formData {
        append("type", type)
        append("category", category)
        append("amount", amount)
        append("month", month)
        append("year", year.toString())
        append("description", description)

        append(
            key = "image",
            value = imageBytes,
            headers = Headers.build {
                append(HttpHeaders.ContentType, "image/jpeg")
                append(HttpHeaders.ContentDisposition, "filename=\"receipt.jpg\"")
            }
        )
    }
}