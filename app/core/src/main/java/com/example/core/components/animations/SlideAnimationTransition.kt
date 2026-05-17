package com.example.core.components.animations

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp

/**
 * Smooth per-item entrance animation using alpha + vertical offset.
 * Uses animateFloatAsState with tween(delayMillis) — no AnimatedVisibility jumps.
 *
 * @param visible  Set to true to trigger the entrance. Flip from false -> true once.
 * @param durationMillis Duration of the animation.
 * @param delayMillis    Delay before this item starts animating (for stagger effect).
 */
@Composable
fun SlideAnimationTransition(
    visible: Boolean = true,
    durationMillis: Int = 400,
    delayMillis: Int = 0,
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis
        ),
        label = "slideAlpha_$delayMillis"
    )

    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else 30f,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis
        ),
        label = "slideOffsetY_$delayMillis"
    )

    androidx.compose.foundation.layout.Box(
        modifier = Modifier
            .alpha(alpha)
            .offset(y = offsetY.dp)
    ) {
        content()
    }
}