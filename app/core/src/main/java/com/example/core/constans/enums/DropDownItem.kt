package com.example.core.constans.enums

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.Color

data class DropDownItem<T> (
    val label: String,
    val value: T,
    val icon: ImageVector,
    val color: Color = Color.Unspecified
)