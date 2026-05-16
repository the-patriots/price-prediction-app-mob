package com.example.presentations.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.Black
import com.example.core.ui.theme.Danger
import com.example.core.ui.theme.Success
import java.text.NumberFormat
import java.util.Locale

@Composable
fun IncomeExpensesBar(
    income: Double = 0.0,
    expense: Double = 0.0
) {
    val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))

    Column(modifier = Modifier.padding(start = 10.dp, top = 10.dp)) {
        Text(text = "Transaksi bulan ini", fontWeight = FontWeight.Medium, color = Black)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "Income",
                    tint = Success
                )

                Text(formatter.format(income))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = Icons.Rounded.KeyboardArrowUp,
                    contentDescription = "Expense",
                    tint = Danger
                )

                Text(formatter.format(expense))
            }
        }
    }

}