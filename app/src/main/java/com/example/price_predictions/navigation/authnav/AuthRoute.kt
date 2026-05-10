package com.example.price_predictions.navigation.authnav

sealed class AuthRoute {
    object Login: AuthRoute()
    object Register: AuthRoute()
}