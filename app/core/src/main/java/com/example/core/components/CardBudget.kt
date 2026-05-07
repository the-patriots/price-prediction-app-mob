package com.example.core.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.theme.Black
import com.example.core.ui.theme.Danger
import com.example.core.ui.theme.PrimaryBlue

@Composable
fun CardBudget() {
    Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Rounded.ShoppingCart, "budget", tint = PrimaryBlue, modifier = Modifier.size(40.dp).padding(end = 10.dp))
                Column {
                    Text(text = "Education", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Black)
                    Text(text = "Education", fontSize = 12.sp, color = Color.Gray)
                }
            }
            Text(text="Rp. 10.000.000", fontWeight = FontWeight.Medium, color = Black)
        }
    }
}