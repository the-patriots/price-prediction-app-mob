package com.example.presentations.cashflow.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core.constans.enums.InputTransactionEnum
import com.example.core.components.CardBudget
import com.example.core.components.YearSelector
import com.example.core.components.animations.SlideAnimationTransition
import com.example.core.ui.theme.Danger
import com.example.core.ui.theme.PrimaryBlue
import com.example.core.ui.theme.Success
import com.example.presentations.cashflow.viewmodel.CashFlowViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CashFlowScreen(
    modifier: Modifier = Modifier,
    selectedMonth: String? = null,
    viewModel: CashFlowViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(selectedMonth) {
        viewModel.updateMonth(selectedMonth)
    }

    LaunchedEffect(state.currentTab) {
        viewModel.loadCashFlows()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TabRow(selectedTabIndex = state.currentTab, contentColor = PrimaryBlue) {
            InputTransactionEnum.TypeCashFlow.entries.forEachIndexed { index, title ->
                Tab(
                    selected = state.currentTab == index,
                    onClick = { viewModel.updateTabType(index) },
                    text = { Text(title.value) },
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        YearSelector(
            year = viewModel.currentYear,
            onYearChange = { viewModel.updateYear(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (state.isLoading) {
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = PrimaryBlue,
                )
            }
        }

        LazyColumn {
            items(count = state.cashFlows.size) { index ->
                val cashflow = state.cashFlows[index]
                SlideAnimationTransition {
                    CardBudget(
                        tint = if (state.currentTab == 0) Danger else Success,
                        icon = if (state.currentTab == 0) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                        category = cashflow.category,
                        info = cashflow.description,
                        amount = cashflow.amount,
                    )

                }
            }
        }
    }
}