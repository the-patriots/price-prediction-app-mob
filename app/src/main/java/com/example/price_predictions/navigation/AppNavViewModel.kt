package com.example.price_predictions.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AppNavViewModel(private val supabase: SupabaseClient) : ViewModel() {

    private val _sessionStatus = MutableStateFlow<SessionStatus>(SessionStatus.LoadingFromStorage)
    val sessionStatus: StateFlow<SessionStatus> = _sessionStatus.asStateFlow()

    init {
        viewModelScope.launch {
            val splashStart = System.currentTimeMillis()

            supabase.auth.sessionStatus.collectLatest { status ->
                if (status !is SessionStatus.LoadingFromStorage) {
                    // Ensure splash is shown for at least 2 seconds
                    val elapsed = System.currentTimeMillis() - splashStart
                    val remaining = 2000L - elapsed
                    if (remaining > 0) delay(remaining)
                }
                _sessionStatus.value = status
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            supabase.auth.signOut()
        }
    }
}
