package com.example.data.auth.datasource

import com.example.data.auth.models.UserInsertModel
import com.example.data.auth.models.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from

interface AuthNetworkDatasource {
    suspend fun login(username: String, password: String): Result<UserModel>
    suspend fun register(username: String, password: String): Result<UserModel>
}

class AuthNetworkDatasourceImpl(private val supabase: SupabaseClient) : AuthNetworkDatasource {
    override suspend fun login(
        username: String,
        password: String,
    ): Result<UserModel> {
        return runCatching {
            withContext(Dispatchers.IO) {
                withTimeout(30_000L) {
                    println("[AuthNetworkDatasource] login start: $username")
                    supabase.auth.signInWith(Email) {
                        email = username
                        this.password = password
                    }
                    println("[AuthNetworkDatasource] signInWith finished")

                    val currentUser = supabase.auth.currentUserOrNull()
                        ?: throw IllegalStateException("User not found after login")

                    UserModel(
                        id = currentUser.id,
                        email = currentUser.email ?: "",
                        username = currentUser.userMetadata?.get("username")?.toString() ?: ""
                    )
                }
            }
        }.recoverCatching { throwable ->
            println("[AuthNetworkDatasource] login failed: ${throwable.message}")
            throw Exception(
                throwable.message ?: "Login failed",
                throwable
            )
        }
    }

    override suspend fun register(
        username: String,
        password: String,
    ): Result<UserModel> {
        return runCatching {
            withContext(Dispatchers.IO) {
                withTimeout(30_000L) {
                    println("[AuthNetworkDatasource] register start: $username")
                    supabase.auth.signUpWith(Email) {
                        email = username
                        this.password = password
                    }
                    println("[AuthNetworkDatasource] signUpWith finished")

                    val currentUser = supabase.auth.currentUserOrNull()
                    if (currentUser != null) {
                        val userInsert = UserInsertModel(
                            id = currentUser.id,
                            name = username.substringBefore("@"),
                            email = currentUser.email ?: username,
                            balance = 0.0
                        )
                        supabase.from("users").insert(userInsert)
                        UserModel(
                            id = currentUser.id,
                            email = currentUser.email ?: "",
                            username = currentUser.userMetadata?.get("username")?.toString() ?: ""
                        )
                    } else {
                        // SignUp might require email confirmation, so user could be null initially
                        UserModel(email = username)
                    }
                }
            }
        }.recoverCatching { throwable ->
            println("[AuthNetworkDatasource] register failed: ${throwable.message}")
            throw Exception(
                throwable.message ?: "Register failed",
                throwable
            )

        }
    }
}
