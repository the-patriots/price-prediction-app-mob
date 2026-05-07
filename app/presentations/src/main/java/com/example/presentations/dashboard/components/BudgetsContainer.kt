package com.example.presentations.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.core.components.CardBudget

@Composable
fun BudgetsContainer() {
    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(count = 10) {
            CardBudget()
        }
    }
}