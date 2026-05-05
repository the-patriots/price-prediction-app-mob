package com.example.data.auth.datasource

interface AuthNetworkDatasource {
    suspend fun login(username: String, password: String): Result<String>
    suspend fun register(username: String, password: String): Result<String>
}

class AuthNetworkDatasourceImpl : AuthNetworkDatasource{
    override suspend fun login(
        username: String,
        password: String,
    ): Result<String> {
        TODO("Not yet implemented")
    }

    override suspend fun register(
        username: String,
        password: String,
    ): Result<String> {
        TODO("Not yet implemented")
    }

}