package com.example.presentations.home.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.theme.Black
import com.example.presentations.home.components.BudgetsContainer
import com.example.presentations.home.components.DashboardAppBar
import com.example.presentations.home.components.IncomeExpensesBar
import com.example.presentations.home.viemodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(topBar = { DashboardAppBar(onMonthSelect = {viewModel.setMonth(it)}) }) {


            Column(
                modifier = Modifier.padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IncomeExpensesBar()
                Text(text = "Budgets", fontSize = 20.sp, fontWeight = FontWeight.Medium)
                HorizontalDivider(
                    color = Black,
                    modifier = Modifier.padding(10.dp),
                    thickness = 1.5f.dp
                )
                BudgetsContainer()
            }
        }

}