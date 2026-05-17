package com.example.presentations.home.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.home.usecases.GetHomeSummaryUseCase
import com.example.domain.home.usecases.GetRecentCashFlowsUseCase
import com.example.presentations.home.states.HomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getHomeSummaryUseCase: GetHomeSummaryUseCase,
    private val getRecentCashFlowsUseCase: GetRecentCashFlowsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun setMonth(m: String?) {
        val month = if (m == "Semua Bulan") "Semua Bulan" else m ?: "Semua Bulan"
        _uiState.update {
            it.copy(month = month)
        }
        loadData()
    }

    fun setYear(y: Int) {
        _uiState.update {
            it.copy(year = y)
        }
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val currentState = _uiState.value
            val month = if (currentState.month == "Semua Bulan") null else currentState.month
            val year = currentState.year

            // Load summary (income & expense)
            val summaryResult = getHomeSummaryUseCase(month = month, year = year)
            summaryResult.onSuccess { summary ->
                _uiState.update {
                    it.copy(
                        income = summary.totalIncome,
                        outcome = summary.totalExpense,
                        balance = summary.totalIncome - summary.totalExpense
                    )
                }
            }

            // Load recent cash flows
            val cashFlowsResult = getRecentCashFlowsUseCase(month = month, year = year)
            cashFlowsResult.onSuccess { cashFlows ->
                _uiState.update {
                    it.copy(recentCashFlows = cashFlows)
                }
            }

            _uiState.update { it.copy(isLoading = false) }
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