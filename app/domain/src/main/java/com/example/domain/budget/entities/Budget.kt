package com.example.domain.budget.entities

import android.graphics.drawable.Icon

data class Budget(
    val name: String,
    val category: String,
    val amount: Int,
    val icon: Icon,
)