package com.example.data.auth.repository

import com.example.domain.auth.repository.AuthRepository

class RepositoryAuthImpl : AuthRepository{
    override suspend fun login(username: String, password: String): Result<String> {
        // Simulate a successful login
        return if (username == "user" && password == "password") {
            Result.success("Login successful")
        } else {
            Result.failure(Exception("Invalid username or password"))
        }
    }

    override suspend fun register(username: String, password: String): Result<String> {
        // Simulate a successful registration
        return if (username.isNotEmpty() && password.isNotEmpty()) {
            Result.success("Registration successful")
        } else {
            Result.failure(Exception("Username and password cannot be empty"))
        }
    }
}