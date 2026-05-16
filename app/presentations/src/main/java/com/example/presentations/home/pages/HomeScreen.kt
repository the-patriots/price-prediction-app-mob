package com.example.presentations.home.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.components.YearSelector
import com.example.core.ui.theme.Black
import com.example.presentations.home.components.BudgetsContainer
import com.example.presentations.home.components.IncomeExpensesBar
import com.example.presentations.home.viemodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    selectedMonth: String? = null,
    viewModel: HomeViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(selectedMonth) {
        if (selectedMonth == "Semua Bulan") {
            viewModel.setMonth(null)
        } else {
            viewModel.setMonth(selectedMonth)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        YearSelector(
            year = uiState.year,
            onYearChange = { viewModel.setYear(it) }
        )

        IncomeExpensesBar(
            income = uiState.income,
            expense = uiState.outcome
        )
        Text(text = "Budgets", fontSize = 20.sp, fontWeight = FontWeight.Medium)
        HorizontalDivider(
            color = Black,
            modifier = Modifier.padding(10.dp),
            thickness = 1.5f.dp
        )
        BudgetsContainer(cashFlows = uiState.recentCashFlows)
    }
}