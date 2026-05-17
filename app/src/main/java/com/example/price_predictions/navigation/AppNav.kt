package com.example.price_predictions.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.price_predictions.navigation.authnav.AuthMainScaffold
import com.example.price_predictions.navigation.mainnav.MainScaffold
import io.github.jan.supabase.gotrue.SessionStatus
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNav(viewModel: AppNavViewModel = koinViewModel()) {
    val sessionStatus by viewModel.sessionStatus.collectAsState()

    AnimatedContent(
        targetState = sessionStatus,
        transitionSpec = {
            fadeIn(animationSpec = tween(400)) togetherWith
                    fadeOut(animationSpec = tween(400))
        },
        label = "authTransition"
    ) { status ->
        when (status) {
            is SessionStatus.LoadingFromStorage -> SplashScreen()
            is SessionStatus.Authenticated -> MainScaffold()
            is SessionStatus.NotAuthenticated -> AuthMainScaffold()
            is SessionStatus.NetworkError -> AuthMainScaffold()
        }
    }
}
