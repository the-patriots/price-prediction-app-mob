package com.example.price_predictions.navigation.authnav

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.presentations.auth.pages.LoginPage
import com.example.presentations.auth.pages.SignUpPage
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthMainScaffold(modifier: Modifier = Modifier, viewModel: AuthNavViewModel = koinViewModel()) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Price Prediction")
            })
        }
    ) {
        NavDisplay(
            backStack = viewModel.authBackStack,
            modifier = Modifier.padding(it),
            entryProvider = entryProvider {
                entry<AuthRoute.Login> {
                    LoginPage(
                        onSignUpClick = { viewModel.authBackStack.add(AuthRoute.Register) }
                    )
                }
                entry<AuthRoute.Register> { SignUpPage(onLoginClick = { viewModel.pop() }) }
            }
        )
    }
}