package com.example.presentations.analytic.state

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.core.constans.enums.InputTransactionEnum

data class AnalyticState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedType: InputTransactionEnum.TypeCashFlow = InputTransactionEnum.TypeCashFlow.PENGELUARAN,
    val selectedYear: Int = 2026,
    val selectedMonth: String? = null,
    val categoryData: List<CategoryAnalytic> = emptyList(),
    val totalAmount: Double = 0.0
) {
    val currentTab: Int
        get() = if (selectedType == InputTransactionEnum.TypeCashFlow.PENGELUARAN) 0 else 1
}

data class CategoryAnalytic(
    val category: String,
    val amount: Double,
    val percentage: Float,
    val color: Color,
    val icon: ImageVector
)
