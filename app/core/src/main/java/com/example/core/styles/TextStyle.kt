package com.example.core.styles

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow

class CommonTextStyle {
}

fun CommonTextStyle.textShadow(): Shadow {
    return Shadow(
        color = Color(0x861C1C1C),
        offset = Offset(4f, 4f),
        blurRadius = 8f
    )
}