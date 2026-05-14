package com.example.presentations.cashflow.state

import android.net.Uri
import com.example.core.constans.enums.DropDownItem
import com.example.core.constans.enums.InputTransactionEnum

data class CashFlowState(
    val typeTab: InputTransactionEnum.TypeCashFlow = InputTransactionEnum.TypeCashFlow.PENGELUARAN,
    val amount: String = "",
    val description: String = "",
    val selectedCategory: DropDownItem<String> = InputTransactionEnum.KategoriPengeluaran.entries.first().item,
    val date: String = "",
    val imageUri: Uri? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: String? = null,
    val isFormValid: Boolean = false,
    val pengeluaranItems: List<DropDownItem<String>> = InputTransactionEnum.KategoriPengeluaran.entries.map { it.item },
    val pemasukanItems: List<DropDownItem<String>> = InputTransactionEnum.KategoriPemasukan.entries.map { it.item }
){
    val currentTab: Int
        get() = typeTab.ordinal

}