package com.example.data.auth.repository

import com.example.data.auth.datasource.AuthNetworkDatasource
import com.example.domain.auth.entities.User
import com.example.domain.auth.repository.AuthRepository

class AuthRepositoryImpl(private val datasource: AuthNetworkDatasource) : AuthRepository{
    override suspend fun login(username: String, password: String): Result<User> {
        return datasource.login(username, password).map { userModel ->
            User(
                id = userModel.id,
                email = userModel.email,
                username = userModel.username
            )
        }
    }

    override suspend fun register(username: String, password: String): Result<User> {
        return datasource.register(username, password).map { userModel ->
            User(
                id = userModel.id,
                email = userModel.email,
                username = userModel.username
            )
        }
    }
}