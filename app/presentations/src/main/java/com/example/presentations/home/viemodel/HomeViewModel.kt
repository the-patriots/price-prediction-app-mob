package com.example.presentations.home.viemodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.budget.usecases.GetBudgetsUseCase
import com.example.presentations.home.states.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getBudgetsUseCase: GetBudgetsUseCase
): ViewModel() {
   private val _uiState  = MutableStateFlow(HomeScreenState())
   val uiState = _uiState.asStateFlow()

    init {
        getBudgets()
    }

    fun setMonth(m: String) {
        _uiState.update {
            it.copy(month = m)
        }
    }

    fun getBudgets() {
        viewModelScope.launch {
            getBudgetsUseCase().fold(
                onSuccess = {
                    _uiState.update { _uiState.value.copy(budgets = it.budgets) }
                },
                onFailure = {
                    println(it.toString())
                    _uiState.update { _uiState.value.copy(isError = true) }

                }
            )
        }
    }
}