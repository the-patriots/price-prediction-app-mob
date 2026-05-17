package com.example.price_predictions.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppNavViewModel(private val supabase: SupabaseClient) : ViewModel() {

    val sessionStatus: StateFlow<SessionStatus> = supabase.auth.sessionStatus
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SessionStatus.LoadingFromStorage
        )

    fun signOut() {
        viewModelScope.launch {
            supabase.auth.signOut()
        }
    }
}
