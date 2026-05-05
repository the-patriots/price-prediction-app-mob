package com.example.presentations.auth.state

import com.example.domain.auth.entities.User

data class LoginState(
    val user : User = User(),
    val isLoading: Boolean = false,
    val error: String? = null
)
