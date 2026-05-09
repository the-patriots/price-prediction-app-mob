package com.example.presentations.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun DashboardAppBar(
    onMonthSelect: (String) -> Unit
) {
    var walletBalance by remember { mutableIntStateOf(0) }

    Surface {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth()
                .height(height = 200.dp)
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
                    text = "Mom's Wallet",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    style = TextStyle(
                        shadow = CommonTextStyle().textShadow()
                    )
                )
                MonthDropdown(onSelect = { onMonthSelect(it) })
            }
            DashboardAppBarWallet(walletBalance)
        }
    }


}

@Composable
fun DashboardAppBarWallet(walletBalance: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        Text(
            text = "Saldo Dompet",
            color = Color.White,
            fontSize = 14.sp,
            style = TextStyle(shadow = CommonTextStyle().textShadow())
        )
        Text(
            text = "Rp. $walletBalance",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            style = TextStyle(shadow = CommonTextStyle().textShadow())

        )
    }
}