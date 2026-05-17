package com.example.presentations.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.example.core.ui.theme.Black

@Composable
fun MonthDropdown(onSelect: (String) -> Unit) {
    var expand by remember { mutableStateOf(false) }
    var selectedMonth by remember { mutableStateOf("Semua Bulan") }
    val months = listOf(
        "Semua Bulan",
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember",
    )

    fun handleSelect(m: String) {
        selectedMonth = m
        expand = false
        onSelect(m)
    }

    Box {
        IconButton(

            onClick = { expand = !expand }, modifier = Modifier
                .width(130.dp)
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
                ) {
                Text(text = selectedMonth, color = Black)
                Icon(
                    Icons.Rounded.KeyboardArrowDown,
                    contentDescription = "More options"
                )
            }

        }
        DropdownMenu(
            offset = DpOffset(0.dp, 10.dp),
            expanded = expand,
            onDismissRequest = { expand = false },
            modifier = Modifier.background(color = Color.White)
        ) {
           months.forEach { month ->
               DropdownMenuItem(
                   text = { Text(text = month, color = Black) },
                   onClick = { handleSelect(month) }
               )
           }
        }
    }
}