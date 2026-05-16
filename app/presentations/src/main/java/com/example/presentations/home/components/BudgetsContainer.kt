package com.example.presentations.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.components.CardBudget
import com.example.core.shareddomain.entities.CashFlow

@Composable
fun BudgetsContainer(cashFlows: List<CashFlow> = emptyList()) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(cashFlows) { cashFlow ->
            CardBudget(
                category = cashFlow.category,
                info = cashFlow.description,
                amount = cashFlow.amount
            )
        }
    }
}