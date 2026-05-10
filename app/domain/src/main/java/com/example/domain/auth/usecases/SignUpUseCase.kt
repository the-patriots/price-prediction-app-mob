package com.example.domain.auth.usecases

import com.example.domain.auth.entities.User
import com.example.domain.auth.repository.AuthRepository

class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<User> {
        return authRepository.register(username, password)
    }
}
