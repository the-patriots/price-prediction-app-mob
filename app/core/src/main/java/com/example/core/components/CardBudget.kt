package com.example.core.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.ui.theme.Black
import com.example.core.ui.theme.PrimaryBlue
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CardBudget(
    category: String = "",
    icon: ImageVector = Icons.Rounded.ShoppingCart,
    info: String? = "",
    amount: Double = 0.0,
    tint: Color = PrimaryBlue,
    result: String? = null,
) {
    val formattedCurrency = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(amount)
    val description = if (info.isNullOrEmpty()) "Tidak ada deskripsi" else info

    val resultColor = when (result) {
        "Di atas rata-rata" -> Color(0xFFE53935)
        "Di bawah rata-rata" -> Color(0xFF43A047)
        "Normal" -> Color(0xFF1E88E5)
        else -> null
    }

    Box(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(icon, "budget", tint = tint, modifier = Modifier.size(40.dp).padding(end = 10.dp))
                Column {
                    Text(text = category, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Black)
                    Text(text = description, fontSize = 12.sp, color = Color.Gray)
                    if (result != null && resultColor != null) {
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(resultColor.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = result,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = resultColor
                            )
                        }
                    }
                }
            }
            Text(text = formattedCurrency, fontWeight = FontWeight.Medium, color = tint)
        }
    }
}