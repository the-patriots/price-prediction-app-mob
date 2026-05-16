package com.example.presentations.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.styles.CommonTextStyle
import com.example.core.styles.textShadow
import com.example.core.ui.theme.PrimaryGradient
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DashboardAppBar(
    walletBalance: Double = 0.0,
    onMonthSelect: (String) -> Unit
) {
    val formattedBalance = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(walletBalance)

    Surface {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(PrimaryGradient),
                    shape = RoundedCornerShape(
                        bottomStart = 35.dp,
                        bottomEnd = 35.dp
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Dompet Ibu",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    style = TextStyle(
                        shadow = CommonTextStyle().textShadow()
                    )
                )
                MonthDropdown(onSelect = { onMonthSelect(it) })
            }

            DashboardAppBarWallet(formattedBalance)
        }
    }
}

@Composable
fun DashboardAppBarWallet(formattedBalance: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Saldo Dompet",
            color = Color.White,
            fontSize = 14.sp,
            style = TextStyle(shadow = CommonTextStyle().textShadow())
        )
        Text(
            text = formattedBalance,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            style = TextStyle(shadow = CommonTextStyle().textShadow())

        )
    }
}