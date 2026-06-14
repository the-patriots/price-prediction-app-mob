package com.example.price_predictions.navigation.mainnav

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.IntOffset
import com.example.core.components.LocalSnackbarHostState
import com.example.core.ui.theme.PrimaryBlue
import com.example.presentations.analytic.pages.AnalyticScreen
import com.example.presentations.cashflow.pages.CashFlowScreen
import com.example.presentations.cashflow.pages.InputTransactionScreen
import com.example.presentations.home.components.DashboardAppBar
import com.example.presentations.home.pages.HomeScreen
import com.example.presentations.home.viemodel.HomeViewModel
import com.example.price_predictions.navigation.AppNavViewModel
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

data class BottomNavItem(
    val label: String,
    val route: MainRoute,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun MainScaffold(
    modifier: Modifier = Modifier,
    viewModel: MainNavViewModel = koinViewModel(),
    appNavViewModel: AppNavViewModel = koinViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val homeViewModel: HomeViewModel = koinViewModel()
    val homeUiState by homeViewModel.uiState.collectAsState()

    // ── Entrance animation trigger ─────────────────────────────────────────
    var barsVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { barsVisible = true }

    // TopBar slides from above
    val topBarOffsetY by animateFloatAsState(
        targetValue = if (barsVisible) 0f else -150f,
        animationSpec = tween(durationMillis = 480, easing = FastOutSlowInEasing),
        label = "topBarOffset"
    )
    val topBarAlpha by animateFloatAsState(
        targetValue = if (barsVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 420, easing = FastOutSlowInEasing),
        label = "topBarAlpha"
    )

    // BottomBar slides from below (slight delay)
    val bottomBarOffsetY by animateFloatAsState(
        targetValue = if (barsVisible) 0f else 150f,
        animationSpec = tween(durationMillis = 480, delayMillis = 80, easing = FastOutSlowInEasing),
        label = "bottomBarOffset"
    )
    val bottomBarAlpha by animateFloatAsState(
        targetValue = if (barsVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 420, delayMillis = 80, easing = FastOutSlowInEasing),
        label = "bottomBarAlpha"
    )
    // ──────────────────────────────────────────────────────────────────────

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

    var previousTabIndex by remember { mutableIntStateOf(0) }
    val currentTabIndex = navItems.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)

    val selectedMonth by viewModel.selectedMonth

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                DashboardAppBar(
                    modifier = Modifier
                        .offset { IntOffset(x = 0, y = topBarOffsetY.roundToInt()) }
                        .alpha(topBarAlpha),
                    walletBalance = homeUiState.balance,
                    onMonthSelect = { month ->
                        viewModel.updateSelectedMonth(month)
                        homeViewModel.setMonth(month)
                    },
                    onLogout = { appNavViewModel.signOut() }
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = PrimaryBlue,
                    modifier = Modifier
                        .offset { IntOffset(x = 0, y = bottomBarOffsetY.roundToInt()) }
                        .alpha(bottomBarAlpha)
                ) {
                    // Each nav item slides up + fades in with per-item delay (100ms step)
                    navItems.forEachIndexed { index, item ->
                        val isSelected = currentRoute == item.route ||
                                (currentRoute != null && currentRoute!!::class == item.route::class)

                        // Per-item entrance: each icon delays 100ms more than previous
                        val itemDelay = 160 + index * 80   // 160, 240, 320, 400ms
                        val itemAlpha by animateFloatAsState(
                            targetValue = if (barsVisible) 1f else 0f,
                            animationSpec = tween(durationMillis = 360, delayMillis = itemDelay, easing = FastOutSlowInEasing),
                            label = "navItemAlpha_$index"
                        )
                        val itemOffsetY by animateFloatAsState(
                            targetValue = if (barsVisible) 0f else 40f,
                            animationSpec = tween(durationMillis = 360, delayMillis = itemDelay, easing = FastOutSlowInEasing),
                            label = "navItemOffset_$index"
                        )

                        NavigationBarItem(
                            modifier = Modifier
                                .alpha(itemAlpha)
                                .offset { IntOffset(0, itemOffsetY.roundToInt()) },
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
                            onClick = {
                                previousTabIndex = currentTabIndex
                                viewModel.navigateTo(item.route)
                            },
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
        ) { paddingValues ->
            val slideDirection = if (currentTabIndex >= previousTabIndex) 1 else -1

            AnimatedContent(
                targetState = currentRoute,
                transitionSpec = {
                    slideInHorizontally(
                        initialOffsetX = { fullWidth -> fullWidth * slideDirection },
                        animationSpec = tween(durationMillis = 300)
                    ) togetherWith slideOutHorizontally(
                        targetOffsetX = { fullWidth -> -fullWidth * slideDirection },
                        animationSpec = tween(durationMillis = 300)
                    )
                },
                label = "tabSlide",
                modifier = Modifier.padding(paddingValues)
            ) { route ->
                when (route) {
                    MainRoute.HomePage -> HomeScreen(selectedMonth = selectedMonth)
                    MainRoute.Transactions -> CashFlowScreen(selectedMonth = selectedMonth, onHistoryDelete = homeViewModel::loadData)
                    MainRoute.InputPage -> InputTransactionScreen()
                    MainRoute.AnalyticPage -> AnalyticScreen(selectedMonth = selectedMonth)
                    else -> HomeScreen(selectedMonth = selectedMonth)
                }
            }
        }
    }
}