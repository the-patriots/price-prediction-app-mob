package com.example.presentations.cashflow.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.constans.enums.DropDownItem
import com.example.core.constans.enums.InputTransactionEnum
import com.example.domain.cashflow.usecases.CheckAiPriceUseCase
import com.example.domain.cashflow.usecases.CreateCashFlowUseCase
import com.example.domain.cashflow.usecases.DeleteCashFlowUseCase
import com.example.domain.cashflow.usecases.EditCashflowUseCase
import com.example.domain.cashflow.usecases.GetCashFlowsUseCase
import com.example.presentations.cashflow.state.CashFlowState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class CashFlowViewModel(
    private val createCashFlowUseCase: CreateCashFlowUseCase,
    private val checkAiPriceUseCase: CheckAiPriceUseCase,
    private val getCashFlowsUseCase: GetCashFlowsUseCase,
    private val editCashflowUseCase: EditCashflowUseCase,
    private val deleteCashFlowUseCase: DeleteCashFlowUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CashFlowState())
    val state = _state.asStateFlow()

    private var _selectedMonth: String? = null
    private var _selectedYear: Int = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)

    val currentYear: Int get() = _selectedYear

    init {
        loadCashFlows()
    }

    fun updateMonth(month: String?) {
        _selectedMonth = if (month == "Semua Bulan") null else month
        loadCashFlows()
    }

    fun updateYear(year: Int) {
        _selectedYear = year
        loadCashFlows()
    }


    fun loadCashFlows() {
        _state.update { it.copy(isLoading = true) }
        val type =
            if (state.value.currentTab == 0) InputTransactionEnum.TypeCashFlow.PENGELUARAN else InputTransactionEnum.TypeCashFlow.PEMASUKKAN
        val month = _selectedMonth ?: ""
        viewModelScope.launch {
            getCashFlowsUseCase(
                type = type,
                month = month,
                year = _selectedYear,
            ).fold(
                onSuccess = { data ->
                    _state.update {
                        it.copy(
                            cashFlows = data,
                            isLoading = false
                        )
                    }
                },
                onFailure = { err ->
                    _state.update {
                        it.copy(
                            error = err.message,
                            isLoading = false
                        )
                    }
                }
            )
        }
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

    fun resetSnackbar() {
        _state.update { it.copy(success = null, error = null) }
    }

    fun submit(id: String? = null) {
        val currentState = _state.value

        if (currentState.amountString.isBlank() || currentState.amountString.toDoubleOrNull() == null) {
            _state.update { it.copy(error = "Amount is invalid or empty", success = null) }
            return
        }

        if (currentState.dateString.isBlank()) {
            _state.update { it.copy(error = "Date is empty", success = null) }
            return
        }

        // If pemasukan (income), bypass AI check and save directly
        if (currentState.currentTab == 1) {
            directSave(id)
            return
        }

        _state.update {
            it.copy(
                isCheckingAi = true,
                isLoading = true,
                error = null,
                success = null
            )
        }

        viewModelScope.launch {
            val category = currentState.payload.cashFlow.category
            val description = currentState.payload.cashFlow.description ?: ""
            val amount = currentState.payload.cashFlow.amount

            val result = checkAiPriceUseCase(
                category = category,
                productName = description,
                price = amount
            )

            result.onSuccess { prediction ->
                val formattedPrice = try {
                    NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                        .format(prediction.marketPrice.toInt())
                } catch (_: Exception) {
                    NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                        .format(0)
                }
                _state.update {
                    it.copy(
                        isCheckingAi = false,
                        isLoading = false,
                        showAiDialog = true,
                        aiEnumText = prediction.marketPrice,
                        aiResultText = "Harga berada di status ${prediction.prediction} dari harga pasar, harga pasar ada pada $formattedPrice"
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

    fun confirmSave(id: String? = null) {
        val currentState = _state.value

        // Set the prediction result into the cashflow payload before saving
        _state.update {
            it.copy(
                isLoading = true,
                error = null,
                success = null,
                showAiDialog = false,
                payload = it.payload.copy(
                    cashFlow = it.payload.cashFlow.copy(result = it.aiEnumText)
                )
            )
        }

        viewModelScope.launch {
            if (!id.isNullOrBlank()) {
                val result = editCashflowUseCase(id, _state.value.payload)
                result.onSuccess { msg ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            success = msg,
                            error = null,
                            amountString = "",
                            dateString = "",
                            aiResultText = "",
                            payload = it.payload.copy(
                                cashFlow = it.payload.cashFlow.copy(
                                    amount = 0.0,
                                    description = "",
                                    month = "",
                                    year = 2026,
                                    result = null
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
                return@launch
            }


            val result = createCashFlowUseCase(_state.value.payload)
            result.onSuccess { msg ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        success = msg,
                        error = null,
                        amountString = "",
                        dateString = "",
                        aiResultText = "",
                        payload = it.payload.copy(
                            cashFlow = it.payload.cashFlow.copy(
                                amount = 0.0,
                                description = "",
                                month = "",
                                year = 2026,
                                result = null
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

    private fun directSave(id: String? = null) {
        _state.update { it.copy(isLoading = true, error = null, success = null) }



        viewModelScope.launch {
            if (!id.isNullOrBlank()) {
                val result = editCashflowUseCase(id, _state.value.payload)
                result.onSuccess { msg ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            success = msg,
                            error = null,
                            amountString = "",
                            dateString = "",
                            aiResultText = "",
                            payload = it.payload.copy(
                                cashFlow = it.payload.cashFlow.copy(
                                    amount = 0.0,
                                    description = "",
                                    month = "",
                                    year = 2026,
                                    result = null
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
                return@launch
            }
            val result = createCashFlowUseCase(_state.value.payload)
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
                                year = 2026,
                                result = null
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

    fun deleteCashFlow(id: String) {
        _state.update { it.copy(isLoading = true, error = null, success = null) }
        viewModelScope.launch {
            deleteCashFlowUseCase(id).fold(
                onSuccess = {
                    _state.update { it.copy(success = "Transaksi berhasil dihapus") }
                    loadCashFlows()
                },
                onFailure = { err ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = err.message ?: "Gagal menghapus transaksi"
                        )
                    }
                }
            )
        }
    }
}