package com.example.core.components.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import kotlinx.coroutines.delay

@Composable
fun SlideAnimationTransition(
    visible: Boolean = true,
    durationMillis: Int = 500,
    delayMillis: Int = 0,
    content: @Composable () -> Unit
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if (visible) {
            delay(delayMillis.toLong())
            isVisible = true
        } else {
            isVisible = false
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { fullHeight -> fullHeight / 3 },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMediumLow
            )
        ) + fadeIn(
            animationSpec = tween(durationMillis = durationMillis)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight / 4 },
            animationSpec = tween(durationMillis = (durationMillis * 0.6f).toInt())
        ) + fadeOut(
            animationSpec = tween(durationMillis = (durationMillis * 0.6f).toInt())
        )
    ) {
        content()
    }
}