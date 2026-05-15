package com.example.presentations.cashflow.state

import android.net.Uri
import com.example.core.constans.enums.DropDownItem
import com.example.core.constans.enums.InputTransactionEnum
import com.example.domain.cashflow.entities.CashFlowPayload
import com.example.core.shareddomain.entities.CashFlow
import com.example.domain.cashflow.entities.CashFlowEntity

data class CashFlowState(
    val payload: CashFlowPayload = CashFlowPayload(
        cashFlow = CashFlow(
            id = null,
            type = InputTransactionEnum.TypeCashFlow.PENGELUARAN.value,
            category = InputTransactionEnum.KategoriPengeluaran.entries.first().item.label,
            amount = 0.0,
            createdAt = null,
            month = "",
            year = 2026,
            description = ""
        )
    ),
    val cashFlows: List<CashFlowEntity> = emptyList(),
    val amountString: String = "",
    val dateString: String = "",
    val selectedCategory: DropDownItem<String> = InputTransactionEnum.KategoriPengeluaran.entries.first().item,
    val isLoading: Boolean = false,
    val isCheckingAi: Boolean = false,
    val showAiDialog: Boolean = false,
    val aiResultText: String = "",
    val error: String? = null,
    val success: String? = null,
    val isFormValid: Boolean = false,
    val pengeluaranItems: List<DropDownItem<String>> = InputTransactionEnum.KategoriPengeluaran.entries.map { it.item },
    val pemasukanItems: List<DropDownItem<String>> = InputTransactionEnum.KategoriPemasukan.entries.map { it.item }
){
    val currentTab: Int
        get() = if (payload.cashFlow.type == InputTransactionEnum.TypeCashFlow.PENGELUARAN.value) 0 else 1
}