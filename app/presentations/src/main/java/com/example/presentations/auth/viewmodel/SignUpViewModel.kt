package com.example.presentations.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.components.Validators
import com.example.domain.auth.usecases.SignUpUseCase
import com.example.presentations.auth.state.SignUpState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.update {
            it.copy(
                email = email,
                isFormValid = validateForm(email, it.password, it.repassword)
            )
        }
    }

    fun onPasswordChange(password: String) {
        _state.update {
            it.copy(
                password = password,
                isFormValid = validateForm(it.email, password, it.repassword)
            )
        }
    }

    fun onRepasswordChange(repassword: String) {
        _state.update {
            it.copy(
                repassword = repassword,
                isFormValid = validateForm(it.email, it.password, repassword)
            )
        }
    }

    private fun validateForm(email: String, password: String, repassword: String): Boolean {
        return Validators.isNotEmpty(email).isValid &&
                Validators.isValidEmail(email).isValid &&
                Validators.isNotEmpty(password).isValid &&
                Validators.isValidPassword(password).isValid &&
                password == repassword &&
                Validators.isNotEmpty(repassword).isValid
    }

    fun signUp() {
        val currentState = _state.value
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
                success = null
            )
        }
        viewModelScope.launch {
            signUpUseCase(currentState.email, currentState.password).fold(
                onSuccess = { data ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = null,
                            success = "Sign Up Successful",
                            user = data
                        )
                    }
                },
                onFailure = { error ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = error.message,
                            success = null
                        )
                    }
                }
            )
        }
    }

    fun clearSnackbarState() {
        _state.update { it.copy(error = null, success = null) }
    }
}