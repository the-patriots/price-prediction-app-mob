package com.example.price_predictions.navigation.mainnav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.core.components.LocalSnackbarHostState
import com.example.presentations.cashflow.pages.InputTransactionScreen
import com.example.presentations.home.components.DashboardAppBar
import com.example.presentations.home.pages.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScaffold(modifier: Modifier = Modifier, viewModel: MainNavViewModel = koinViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = { DashboardAppBar(onMonthSelect = {}) },
            bottomBar = {
                BottomAppBar(actions = {
                    Text("item 1")
                    Text("item 2")
                    Text("item 3")
                })
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                NavDisplay(
                    backStack = viewModel.mainBackStack,
                    entryProvider = entryProvider {
                        entry<MainRoute.HomePage> {
                            HomeScreen()
                        }
                        entry<MainRoute.InputPage> {
                            InputTransactionScreen()
                        }
                    }

                )
            }
        }
    }

}