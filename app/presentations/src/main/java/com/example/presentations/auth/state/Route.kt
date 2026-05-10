package com.example.presentations.auth.state

import androidx.navigation3.runtime.NavKey

sealed class Route {
    data class Dashboard(
        val name: String = ""
    )
    data object Home
}