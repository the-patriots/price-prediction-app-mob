package com.example.presentations.home.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core.components.CardBudget
import com.example.domain.budget.entities.Budget

@Composable
fun BudgetsContainer(budgets: List<Budget>) {
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(count = budgets.size) {
            CardBudget()
        }
    }
}