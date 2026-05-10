package com.example.domain.auth.repository

import com.example.domain.auth.entities.User

interface AuthRepository {
    suspend fun login(username: String, password: String): Result<User>
    suspend fun register(username: String, password: String): Result<User>
}