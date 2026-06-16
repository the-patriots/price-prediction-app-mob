package com.example.price_predictions.navigation.mainnav

sealed class MainRoute {
    object HomePage: MainRoute()
    data class InputPage(val id: String? = null, val type: String? = null): MainRoute()
    object AnalyticPage: MainRoute()

    object Transactions: MainRoute()
}