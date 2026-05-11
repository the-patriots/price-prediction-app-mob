package com.example.price_predictions.navigation.mainnav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.presentations.home.pages.HomeScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScaffold(modifier: Modifier = Modifier, viewModel: MainNavViewModel = koinViewModel()) {
    Scaffold(
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
                }
            )
        }
    }

}