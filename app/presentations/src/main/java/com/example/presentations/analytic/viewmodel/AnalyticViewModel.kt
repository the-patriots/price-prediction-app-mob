package com.example.presentations.analytic.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.constans.enums.InputTransactionEnum
import com.example.domain.analytic.usecases.GetAnalyticDataUseCase
import com.example.presentations.analytic.state.AnalyticState
import com.example.presentations.analytic.state.CategoryAnalytic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnalyticViewModel(
    private val getAnalyticDataUseCase: GetAnalyticDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AnalyticState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun updateTabType(index: Int) {
        val selectedType = InputTransactionEnum.TypeCashFlow.entries[index]
        _state.update { it.copy(selectedType = selectedType) }
        loadData()
    }

    fun updateYear(year: Int) {
        _state.update { it.copy(selectedYear = year) }
        loadData()
    }

    fun updateMonth(month: String?) {
        _state.update { it.copy(selectedMonth = month) }
        loadData()
    }

    fun loadData() {
        val currentState = _state.value
        _state.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = getAnalyticDataUseCase(currentState.selectedYear, currentState.selectedMonth)

            result.onSuccess { cashFlows ->
                val filteredFlows = cashFlows.filter { cf ->
                    cf.type == currentState.selectedType.value
                }

                val grouped = filteredFlows.groupBy { it.category }
                val totalAmount = filteredFlows.sumOf { it.amount }

                val categoryAnalytics = grouped.map { (category, items) ->
                    val amount = items.sumOf { it.amount }
                    val percentage = if (totalAmount > 0) (amount / totalAmount * 100).toFloat() else 0f
                    val (color, icon) = getCategoryColorAndIcon(category, currentState.selectedType)

                    CategoryAnalytic(
                        category = category,
                        amount = amount,
                        percentage = percentage,
                        color = color,
                        icon = icon
                    )
                }.sortedByDescending { it.amount }

                _state.update {
                    it.copy(
                        isLoading = false,
                        categoryData = categoryAnalytics,
                        totalAmount = totalAmount,
                        error = null
                    )
                }
            }.onFailure { err ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = err.message ?: "Gagal memuat data analitik"
                    )
                }
            }
        }
    }

    private fun getCategoryColorAndIcon(
        category: String,
        type: InputTransactionEnum.TypeCashFlow
    ): Pair<Color, androidx.compose.ui.graphics.vector.ImageVector> {
        return when (type) {
            InputTransactionEnum.TypeCashFlow.PENGELUARAN -> {
                val entry = InputTransactionEnum.KategoriPengeluaran.entries.find {
                    it.item.label == category || it.item.value == category
                }
                Pair(
                    entry?.item?.color ?: Color(0xFF90A4AE),
                    entry?.item?.icon ?: Icons.Default.Info
                )
            }
            InputTransactionEnum.TypeCashFlow.PEMASUKKAN -> {
                val entry = InputTransactionEnum.KategoriPemasukan.entries.find {
                    it.item.label == category || it.item.value == category
                }
                Pair(
                    entry?.item?.color ?: Color(0xFF607D8B),
                    entry?.item?.icon ?: Icons.Default.Info
                )
            }
        }
    }
}
