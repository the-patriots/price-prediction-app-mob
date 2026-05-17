package com.example.presentations.home.pages

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.core.components.YearSelector
import com.example.core.ui.theme.Black
import com.example.presentations.home.components.BudgetsContainer
import com.example.presentations.home.components.IncomeExpensesBar
import com.example.presentations.home.viemodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

/** Smooth per-item slide-up + fade for HomeScreen children. */
@Composable
private fun HomeItem(
    delayMillis: Int,
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 400, delayMillis = delayMillis, easing = FastOutSlowInEasing),
        label = "homeItemAlpha_$delayMillis"
    )
    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else 40f,
        animationSpec = tween(durationMillis = 400, delayMillis = delayMillis, easing = FastOutSlowInEasing),
        label = "homeItemOffset_$delayMillis"
    )
    androidx.compose.foundation.layout.Box(
        modifier = modifier
            .alpha(alpha)
            .offset { IntOffset(0, offsetY.roundToInt()) }
    ) {
        content()
    }
}

@Composable
fun HomeScreen(
    selectedMonth: String? = null,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { visible = true }

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

        // 1. Year selector — delay 0ms
        HomeItem(delayMillis = 0, visible = visible, modifier = Modifier.fillMaxWidth()) {
            YearSelector(
                modifier = Modifier.padding(horizontal = 16.dp),
                year = uiState.year,
                onYearChange = { viewModel.setYear(it) }
            )
        }

        // 2. Income / Expense bar — delay 100ms
        HomeItem(delayMillis = 100, visible = visible, modifier = Modifier.fillMaxWidth()) {
            IncomeExpensesBar(
                income = uiState.income,
                expense = uiState.outcome
            )
        }

        // 3. Divider — delay 200ms
        HomeItem(delayMillis = 200, visible = visible, modifier = Modifier.fillMaxWidth()) {
            HorizontalDivider(
                color = Black,
                modifier = Modifier.padding(10.dp),
                thickness = 1.5f.dp
            )
        }

        // 4. Transactions list — delay 300ms
        HomeItem(delayMillis = 300, visible = visible, modifier = Modifier.fillMaxWidth()) {
            BudgetsContainer(cashFlows = uiState.recentCashFlows)
        }
    }
}