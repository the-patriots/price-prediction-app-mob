package com.example.core.components.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SlideAnimationTransition(
    visible: Boolean = true,
    durationMillis: Int = 500,
    delayMillis: Int = 0,
    content: @Composable () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        isVisible = visible
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it / 2 },
            animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)
        ) + fadeIn(animationSpec = tween(durationMillis = durationMillis, delayMillis = delayMillis)),
        exit = slideOutVertically(
            targetOffsetY = { it / 2 },
            animationSpec = tween(durationMillis = durationMillis)
        ) + fadeOut(animationSpec = tween(durationMillis = durationMillis))
    ) {
        content()
    }
}