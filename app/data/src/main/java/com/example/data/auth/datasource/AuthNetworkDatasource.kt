package com.example.data.auth.datasource

import com.example.data.auth.models.UserModel
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
        return try {
            supabase.auth.signInWith(Email) {
                email = username
                this.password = password
            }
            val currentUser = supabase.auth.currentUserOrNull()
            if (currentUser != null) {
                Result.success(
                    UserModel(
                        id = currentUser.id,
                        email = currentUser.email ?: "",
                        username = currentUser.userMetadata?.get("username")?.toString() ?: ""
                    )
                )
            } else {
                Result.failure(Exception("User not found after login"))
            }
        } catch (_: Exception) {
            Result.failure(Exception("Something went wrong"))
        }
    }

    override suspend fun register(
        username: String,
        password: String,
    ): Result<UserModel> {
        return try {
            supabase.auth.signUpWith(Email) {
                email = username
                this.password = password
            }
            val currentUser = supabase.auth.currentUserOrNull()
            if (currentUser != null) {
                supabase.from("users").insert(currentUser)
                Result.success(
                    UserModel(
                        id = currentUser.id,
                        email = currentUser.email ?: "",
                        username = currentUser.userMetadata?.get("username")?.toString() ?: ""
                    )
                )
            } else {
                // SignUp might require email confirmation, so user could be null initially
                Result.success(UserModel(email = username))
            }
        } catch (_: Exception) {
            Result.failure(Exception("Something went wrong"))

        }
    }
}
