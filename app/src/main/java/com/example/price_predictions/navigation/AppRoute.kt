package com.example.price_predictions.navigation

sealed class AppRoute {
    object Splash : AppRoute()
    object Main : AppRoute()
    object Auth : AppRoute()
}