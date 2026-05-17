package com.example.presentations.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.components.CardBudget
import com.example.core.constans.enums.InputTransactionEnum
import com.example.core.shareddomain.entities.CashFlow
import com.example.core.ui.theme.Danger
import com.example.core.ui.theme.Success

@Composable
fun BudgetsContainer(cashFlows: List<CashFlow> = emptyList()) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(cashFlows) { cashFlow ->
        val icon = if (cashFlow.type == InputTransactionEnum.TypeCashFlow.PEMASUKKAN.value) Icons.Rounded.KeyboardArrowDown else Icons.Rounded.KeyboardArrowUp
        val tintColor = if (cashFlow.type == InputTransactionEnum.TypeCashFlow.PEMASUKKAN.value) Success else Danger
            CardBudget(
                category = cashFlow.category,
                info = cashFlow.description,
                amount = cashFlow.amount,
                result = cashFlow.result,
                icon = icon,
                tint = tintColor
            )
        }
    }
}