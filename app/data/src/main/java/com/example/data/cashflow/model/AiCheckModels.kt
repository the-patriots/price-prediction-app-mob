package com.example.data.cashflow.model

import com.example.domain.cashflow.entities.AiCheck
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.Serializable

@Serializable
data class PredictPricePayload(
    val category: String,
    val price: Double,
    val productName: String
)

@Serializable
data class PredictPriceResponse(
    val marketPrice: String,
    val prediction: String){
    fun toDomain(): AiCheck {
        return AiCheck(
            prediction = prediction,
            marketPrice = marketPrice
        )
    }

    companion object {
        fun fromRawJson(rawJson: String): PredictPriceResponse {
            val jsonObject = Json.parseToJsonElement(rawJson).jsonObject
            return PredictPriceResponse(
                marketPrice = jsonObject.stringValue("marketPrice", "market_price", "marketprice"),
                prediction = jsonObject.stringValue("prediction", "predictio", "result")
            )
        }
    }
}

private fun JsonObject.stringValue(vararg keys: String): String {
    for (key in keys) {
        val primitive = this[key] as? JsonPrimitive ?: continue
        primitive.contentOrNull?.let { return it }
        return primitive.toString().trim('"')
    }
    return ""
}
