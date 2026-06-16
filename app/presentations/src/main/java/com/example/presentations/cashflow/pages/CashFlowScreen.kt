package com.example.presentations.cashflow.pages

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.components.CardBudget
import com.example.core.components.YearSelector
import com.example.core.components.animations.SlideAnimationTransition
import com.example.core.constans.enums.InputTransactionEnum
import com.example.core.ui.theme.Danger
import com.example.core.ui.theme.PrimaryBlue
import com.example.core.ui.theme.Success
import com.example.presentations.cashflow.viewmodel.CashFlowViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashFlowScreen(
    modifier: Modifier = Modifier,
    selectedMonth: String? = null,
    viewModel: CashFlowViewModel = koinViewModel(),
    onHistoryDelete: () -> Unit = {},
    onNavigateTo:((String, Any?) -> Unit)? = null
) {
    val state by viewModel.state.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }

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
        TabRow(
            selectedTabIndex = state.currentTab,
            contentColor = PrimaryBlue,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    color = PrimaryBlue,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[state.currentTab])
                )
            }
        ) {
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
            items(
                count = state.cashFlows.size,
                key = { index -> state.cashFlows[index].id }
            ) { index ->
                val cashflow = state.cashFlows[index]
                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { value ->
                        if (value == SwipeToDismissBoxValue.EndToStart) {
                            viewModel.deleteCashFlow(cashflow.id)
                            onHistoryDelete()
                            true
                        } else {
                            false
                        }
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    backgroundContent = {
                        val color by animateColorAsState(
                            targetValue = when (dismissState.targetValue) {
                                SwipeToDismissBoxValue.EndToStart -> Danger
                                else -> Color.Transparent
                            },
                            label = "swipeColor"
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                                .background(color)
                                .padding(horizontal = 20.dp)
                                .combinedClickable(
                                    onLongClick = {},
                                    interactionSource = interactionSource,
                                    indication = LocalIndication.current,
                                    onClick = {},
                                ),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Transparent
                            )
                        }
                    }
                ) {
                    SlideAnimationTransition {
                        CardBudget(
                            tint = if (state.currentTab == 0) Danger else Success,
                            icon = if (state.currentTab == 0) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown,
                            category = cashflow.category,
                            info = cashflow.description,
                            amount = cashflow.amount,
                            result = cashflow.result,
                            withOption = true,
                            onEdit = {onNavigateTo?.invoke("edit", cashflow.id) },
                            onDelete = {
                                viewModel.deleteCashFlow(cashflow.id)
                                onHistoryDelete()
                            }
                        )
                    }
                }
            }
        }
    }
}