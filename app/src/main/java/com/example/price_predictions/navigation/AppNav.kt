package com.example.price_predictions.navigation

import androidx.compose.runtime.Composable
import com.example.price_predictions.navigation.authnav.AuthMainScaffold
import com.example.price_predictions.navigation.mainnav.MainScaffold

@Composable
fun AppNav(appNav: AppNavigationState = AppNavigationState()) {
    when(appNav.currentRoute){
        is AppRoute.Auth -> AuthMainScaffold()
        is AppRoute.Main -> MainScaffold()
    }
}
