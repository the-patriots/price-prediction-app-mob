package com.example.core.constans.enums

import androidx.compose.ui.graphics.vector.ImageVector

data class DropDownItem<T> (
    val label: String,
    val value: T,
    val icon: ImageVector,
)