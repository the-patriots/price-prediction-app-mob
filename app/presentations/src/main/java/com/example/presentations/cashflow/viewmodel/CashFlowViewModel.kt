package com.example.presentations.cashflow.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.constans.enums.DropDownItem
import com.example.core.constans.enums.InputTransactionEnum
import com.example.core.shareddomain.entities.CashFlow
import com.example.domain.cashflow.entities.CashFlowPayload
import com.example.domain.cashflow.usecases.CreateCashFlowUseCase
import com.example.presentations.cashflow.state.CashFlowState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class CashFlowViewModel(
    private val createCashFlowUseCase: CreateCashFlowUseCase
): ViewModel() {
    private val _state = MutableStateFlow(CashFlowState())
    val state = _state.asStateFlow()

    fun updateTabType(index: Int) {
        val defaultCategory = if (index == 0) {
            InputTransactionEnum.KategoriPengeluaran.entries.first().item
        } else {
            InputTransactionEnum.KategoriPemasukan.entries.first().item
        }
        val selectedTab = InputTransactionEnum.TypeCashFlow.entries[index]
        _state.update { 
            it.copy(
                typeTab = selectedTab,
                selectedCategory = defaultCategory
            ) 
        }
    }

    fun updateAmount(amount: String) {
        _state.update { it.copy(amount = amount) }
    }

    fun updateCategory(category: DropDownItem<String>) {
        _state.update { it.copy(selectedCategory = category) }
    }

    fun updateDate(date: String) {
        _state.update { it.copy(date = date) }
    }

    fun updateDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun updateImageUri(uri: Uri?) {
        _state.update { it.copy(imageUri = uri) }
    }

    fun submit(fileResolver: (Uri) -> File?) {
        val currentState = _state.value

        if (currentState.amount.isBlank() || currentState.amount.toDoubleOrNull() == null) {
            _state.update { it.copy(error = "Amount is invalid or empty", success = null) }
            return
        }

        if (currentState.date.isBlank()) {
            _state.update { it.copy(error = "Date is empty", success = null) }
            return
        }

        val type = currentState.typeTab.value
        val category = currentState.selectedCategory.label
        val amount = currentState.amount.toDoubleOrNull() ?: 0.0
        val description = currentState.description
        val month = currentState.date.split(" ").firstOrNull() ?: "May"
        val year = currentState.date.split(" ").lastOrNull()?.toIntOrNull() ?: 2026

        var resolvedFile: File? = null
        if (currentState.imageUri != null) {
            resolvedFile = fileResolver(currentState.imageUri)
        }

        val cashFlow = CashFlow(
            id = null,
            type = type,
            category = category,
            amount = amount,
            createdAt = null,
            month = month,
            year = year,
            description = description.ifBlank { null }
        )

        val payload = CashFlowPayload(
            image = resolvedFile,
            cashFlow = cashFlow
        )

        _state.update { it.copy(isLoading = true, error = null, success = null) }

        viewModelScope.launch {
            val result = createCashFlowUseCase(payload)
            result.onSuccess { msg ->
                _state.update { 
                    it.copy(
                        isLoading = false,
                        success = msg,
                        error = null,
                        amount = "",
                        description = "",
                        date = "",
                        imageUri = null
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
}