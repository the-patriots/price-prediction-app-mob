package com.example.presentations.auth.state

import com.example.domain.auth.entities.User

data class SignUpState(
    val user : User = User(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
    val email: String = "",
    val password: String = "",
    val repassword: String = "",
    val isFormValid: Boolean = false
)
