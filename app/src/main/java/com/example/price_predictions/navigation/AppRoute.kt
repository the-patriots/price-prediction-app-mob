package com.example.price_predictions.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

sealed class AppRoute {
    object Main: AppRoute()
    object Auth: AppRoute()
}

class AppNavigationState{
    var currentRoute: AppRoute by mutableStateOf<AppRoute>(AppRoute.Auth)
}