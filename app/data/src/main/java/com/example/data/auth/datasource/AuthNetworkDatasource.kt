package com.example.data.auth.datasource

import retrofit2.http.GET

interface AuthNetworkDatasource {
    @GET()
    suspend fun login(username: String, password: String): Result<String>
    suspend fun register(username: String, password: String): Result<String>
}

