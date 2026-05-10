package com.example.price_predictions.navigation.authnav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class AuthNavViewModel(): ViewModel() {
    val authBackStack = mutableStateListOf<Any>(AuthRoute.Login)

    fun pop(): Boolean {
        return if (authBackStack.size > 1) {
            authBackStack.removeAt(authBackStack.size - 1)
            true
        } else {
            false
        }
    }
}