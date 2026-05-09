package com.example.presentations.home.viemodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.presentations.home.states.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel: ViewModel() {
   private val _uiState  = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    fun setMonth(m: String) {
        _uiState.update {
            it.copy(month = m)
        }
    }
}