package com.example.price_predictions.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.example.presentations.auth.pages.LoginPage

@Composable
fun NavHost() {
    val backStack = remember { mutableStateListOf(Route.Home) }

    NavDisplay(
        backStack= backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider = {key ->
            when(key) {
                is Route.Home -> NavEntry(key) {
                    LoginPage()
                }
            }
        }
    )
}