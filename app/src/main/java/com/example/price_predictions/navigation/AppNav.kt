package com.example.price_predictions.navigation

import androidx.compose.runtime.Composable
import com.example.presentations.home.pages.HomeScreen
import com.example.price_predictions.navigation.authnav.AuthMainScaffold

@Composable
fun AppNav(appNav: AppNavigationState = AppNavigationState()) {
    when(appNav.currentRoute){
        is AppRoute.Auth -> AuthMainScaffold()
        is AppRoute.Main -> HomeScreen()
    }
}
