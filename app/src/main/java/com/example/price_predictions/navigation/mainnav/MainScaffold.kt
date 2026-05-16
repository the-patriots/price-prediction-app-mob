package com.example.price_predictions.navigation.mainnav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.core.components.LocalSnackbarHostState
import com.example.core.ui.theme.PrimaryBlue
import com.example.presentations.analytic.pages.AnalyticScreen
import com.example.presentations.cashflow.pages.CashFlowScreen
import com.example.presentations.cashflow.pages.InputTransactionScreen
import com.example.presentations.home.components.DashboardAppBar
import com.example.presentations.home.pages.HomeScreen
import com.example.presentations.home.viemodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

data class BottomNavItem(
    val label: String,
    val route: MainRoute,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun MainScaffold(modifier: Modifier = Modifier, viewModel: MainNavViewModel = koinViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val homeViewModel: HomeViewModel = koinViewModel()
    val homeUiState by homeViewModel.uiState.collectAsState()

    val navItems = listOf(
        BottomNavItem(
            label = "Home",
            route = MainRoute.HomePage,
            selectedIcon = Icons.Rounded.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavItem(
            label = "Transaksi",
            route = MainRoute.Transactions,
            selectedIcon = Icons.Rounded.Menu,
            unselectedIcon = Icons.Outlined.Menu
        ),
        BottomNavItem(
            label = "Input",
            route = MainRoute.InputPage,
            selectedIcon = Icons.Rounded.Add,
            unselectedIcon = Icons.Outlined.Add
        ),
        BottomNavItem(
            label = "Analytic",
            route = MainRoute.AnalyticPage,
            selectedIcon = Icons.Rounded.DateRange,
            unselectedIcon = Icons.Outlined.DateRange
        )
    )

    val currentRoute by remember {
        derivedStateOf { viewModel.mainBackStack.lastOrNull() }
    }

    val selectedMonth by viewModel.selectedMonth

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                DashboardAppBar(
                    walletBalance = homeUiState.balance,
                    onMonthSelect = { month ->
                        viewModel.updateSelectedMonth(month)
                        // Directly update HomeViewModel so balance updates even on other tabs
                        homeViewModel.setMonth(month)
                    }
                )
            },
            bottomBar = {
                NavigationBar(containerColor = PrimaryBlue) {
                    navItems.forEach { item ->
                        val isSelected = currentRoute == item.route ||
                                (currentRoute != null && currentRoute!!::class == item.route::class)

                        NavigationBarItem(
                            colors = NavigationBarItemColors(
                                selectedIconColor = PrimaryBlue,
                                selectedTextColor = Color.White,
                                selectedIndicatorColor = Color.White,
                                unselectedIconColor = Color.White,
                                unselectedTextColor = Color.White,
                                disabledIconColor = Color.Gray,
                                disabledTextColor = Color.Gray
                            ),
                            selected = isSelected,
                            onClick = { viewModel.navigateTo(item.route) },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                NavDisplay(
                    backStack = viewModel.mainBackStack,
                    entryProvider = entryProvider {
                        entry<MainRoute.HomePage> {
                            HomeScreen(
                                selectedMonth = selectedMonth
                            )
                        }
                        entry<MainRoute.Transactions> {
                            CashFlowScreen(
                                selectedMonth = selectedMonth
                            )
                        }
                        entry<MainRoute.InputPage> {
                            InputTransactionScreen()
                        }
                        entry<MainRoute.AnalyticPage> {
                            AnalyticScreen(
                                selectedMonth = selectedMonth
                            )
                        }
                    }

                )
            }
        }
    }
}