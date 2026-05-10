package com.example.core.components

import android.util.Patterns

object Validators {
    fun isNotEmpty(text: String): ValidationResult {
        return if (text.isBlank()) {
            ValidationResult(false, "Cannot be empty")
        } else {
            ValidationResult(true)
        }
    }

    fun isValidEmail(email: String): ValidationResult {
        return if (email.isBlank()) {
            ValidationResult(false, "Email cannot be empty")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidationResult(false, "Invalid email format")
        } else {
            ValidationResult(true)
        }
    }

    fun isValidPassword(password: String): ValidationResult {
        return if (password.length < 8) {
            ValidationResult(false, "Password must be at least 8 characters")
        } else if (!password.any { it.isUpperCase() }) {
            ValidationResult(false, "Password must contain at least one uppercase letter")
        } else if (!password.any { it.isDigit() }) {
            ValidationResult(false, "Password must contain at least one digit")
        } else {
            ValidationResult(true)
        }
    }
}

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)
