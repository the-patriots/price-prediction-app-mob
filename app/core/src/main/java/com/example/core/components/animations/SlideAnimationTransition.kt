package com.example.core.components.animations

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

/**
 * Smooth per-item entrance: fade + slide up.
 *
 * Uses internal [isVisible] state that always starts at `false` and
 * transitions to `true` via LaunchedEffect — so even `visible = true`
 * (hardcoded) correctly triggers the animation from the initial render.
 *
 * @param visible      Pass `true` to play entrance. Flip false→true to re-trigger.
 * @param durationMillis  Animation duration in ms.
 * @param delayMillis     Stagger delay in ms before this item starts animating.
 */
@Composable
fun SlideAnimationTransition(
    visible: Boolean = true,
    durationMillis: Int = 400,
    delayMillis: Int = 0,
    content: @Composable () -> Unit
) {
    // Always starts false so the animation fires even when visible=true from the start
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        isVisible = visible
    }

    val alpha by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            easing = FastOutSlowInEasing
        ),
        label = "slideAlpha_$delayMillis"
    )

    val offsetY by animateFloatAsState(
        targetValue = if (isVisible) 0f else 30f,
        animationSpec = tween(
            durationMillis = durationMillis,
            delayMillis = delayMillis,
            easing = FastOutSlowInEasing
        ),
        label = "slideOffsetY_$delayMillis"
    )

    Box(
        modifier = Modifier
            .alpha(alpha)
            .offset { IntOffset(0, offsetY.roundToInt()) }
    ) {
        content()
    }
}