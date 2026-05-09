package com.example.data.budget.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
data class Budget(
    @PrimaryKey val id: Int,
    val category: String,
    val amount: Int,
    val month: String,
    val year: Int,
    val icon: String,
)