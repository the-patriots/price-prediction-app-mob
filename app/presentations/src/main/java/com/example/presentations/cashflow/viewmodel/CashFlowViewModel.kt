package com.example.presentations.cashflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.constans.enums.DropDownItem
import com.example.core.constans.enums.InputTransactionEnum
import com.example.domain.cashflow.usecases.CheckAiPriceUseCase
import com.example.domain.cashflow.usecases.CreateCashFlowUseCase
import com.example.domain.cashflow.usecases.GetCashFlowsUseCase
import com.example.presentations.cashflow.state.CashFlowState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CashFlowViewModel(
    private val createCashFlowUseCase: CreateCashFlowUseCase,
    private val checkAiPriceUseCase: CheckAiPriceUseCase,
    private val getCashFlowsUseCase: GetCashFlowsUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(CashFlowState())
    val state = _state.asStateFlow()

    init {
        loadCashFlows()
    }


    fun loadCashFlows() {
        _state.update { it.copy(isLoading = true) }
        val type = if (state.value.currentTab == 0 ) InputTransactionEnum.TypeCashFlow.PENGELUARAN else InputTransactionEnum.TypeCashFlow.PEMASUKKAN
        viewModelScope.launch {
            getCashFlowsUseCase(
                type = type,
                month = "Mei",
                year = 2026,
            ).fold(
                onSuccess = { data -> _state.update { it.copy(cashFlows = data) }},
                onFailure = {err -> _state.update { it.copy(error = err.message) }}
            )
        }
        _state.update { it.copy(isLoading = false) }
    }

    fun updateTabType(index: Int) {
        val defaultCategory = if (index == 0) {
            InputTransactionEnum.KategoriPengeluaran.entries.first().item
        } else {
            InputTransactionEnum.KategoriPemasukan.entries.first().item
        }
        val selectedTab = InputTransactionEnum.TypeCashFlow.entries[index]
        _state.update { 
            it.copy(
                payload = it.payload.copy(
                    cashFlow = it.payload.cashFlow.copy(
                        type = selectedTab.value,
                        category = defaultCategory.label
                    )
                ),
                selectedCategory = defaultCategory
            ) 
        }
    }

    fun updateAmount(amount: String) {
        val parsedAmount = amount.toDoubleOrNull() ?: 0.0
        _state.update {
            it.copy(
                amountString = amount,
                payload = it.payload.copy(
                    cashFlow = it.payload.cashFlow.copy(amount = parsedAmount)
                )
            )
        }
    }

    fun updateCategory(category: DropDownItem<String>) {
        _state.update { 
            it.copy(
                selectedCategory = category,
                payload = it.payload.copy(
                    cashFlow = it.payload.cashFlow.copy(category = category.label)
                )
            ) 
        }
    }

    fun updateDate(date: String) {
        val month = date.split(" ").firstOrNull() ?: ""
        val year = date.split(" ").lastOrNull()?.toIntOrNull() ?: 2026
        _state.update { 
            it.copy(
                dateString = date,
                payload = it.payload.copy(
                    cashFlow = it.payload.cashFlow.copy(
                        month = month,
                        year = year
                    )
                )
            ) 
        }
    }

    fun updateDescription(description: String) {
        _state.update { 
            it.copy(
                payload = it.payload.copy(
                    cashFlow = it.payload.cashFlow.copy(description = description)
                )
            ) 
        }
    }

    fun submit() {
        val currentState = _state.value

        if (currentState.amountString.isBlank() || currentState.amountString.toDoubleOrNull() == null) {
            _state.update { it.copy(error = "Amount is invalid or empty", success = null) }
            return
        }

        if (currentState.dateString.isBlank()) {
            _state.update { it.copy(error = "Date is empty", success = null) }
            return
        }

        _state.update { it.copy(isCheckingAi = true, isLoading = true, error = null, success = null) }

        viewModelScope.launch {
            val description = currentState.payload.cashFlow.description ?: ""
            val amount = currentState.payload.cashFlow.amount
            
            val result = checkAiPriceUseCase(description, amount)
            
            result.onSuccess { msg ->
                _state.update {
                    it.copy(
                        isCheckingAi = false,
                        isLoading = false,
                        showAiDialog = true,
                        aiResultText = msg
                    )
                }
            }.onFailure { err ->
                _state.update {
                    it.copy(
                        isCheckingAi = false,
                        isLoading = false,
                        error = "Failed to check AI: ${err.message}"
                    )
                }
            }
        }
    }

    fun confirmSave() {
        val currentState = _state.value

        _state.update { it.copy(isLoading = true, error = null, success = null, showAiDialog = false) }

        viewModelScope.launch {
            val result = createCashFlowUseCase(currentState.payload)
            result.onSuccess { msg ->
                _state.update { 
                    it.copy(
                        isLoading = false,
                        success = msg,
                        error = null,
                        amountString = "",
                        dateString = "",
                        payload = it.payload.copy(
                            cashFlow = it.payload.cashFlow.copy(
                                amount = 0.0,
                                description = "",
                                month = "",
                                year = 2026
                            )
                        )
                    )
                }
            }.onFailure { err ->
                _state.update { 
                    it.copy(
                        isLoading = false,
                        error = err.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }

    fun cancelSave() {
        _state.update { it.copy(showAiDialog = false, aiResultText = "") }
    }
}