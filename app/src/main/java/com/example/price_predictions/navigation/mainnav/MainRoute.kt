package com.example.price_predictions.navigation.mainnav

sealed class MainRoute {
    object HomePage: MainRoute()
    object InputPage: MainRoute()
}