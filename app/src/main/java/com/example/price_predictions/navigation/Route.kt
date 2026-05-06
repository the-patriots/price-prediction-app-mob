package com.example.price_predictions.navigation

import androidx.navigation3.runtime.NavKey

sealed class Route {
    data class Dashboard(
        val name: String = ""
    )
    data object Home: NavKey
}