package com.example.presentations.home.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.offset
import com.example.core.styles.CommonTextStyle
import com.example.core.styles.textShadow
import com.example.core.ui.theme.PrimaryGradient
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

/** Smooth per-item slide-down + fade for AppBar children. */
@Composable
private fun AppBarItem(
    delayMillis: Int,
    visible: Boolean,
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 380, delayMillis = delayMillis, easing = FastOutSlowInEasing),
        label = "appBarItemAlpha_$delayMillis"
    )
    val offsetY by animateFloatAsState(
        targetValue = if (visible) 0f else -24f,
        animationSpec = tween(durationMillis = 380, delayMillis = delayMillis, easing = FastOutSlowInEasing),
        label = "appBarItemOffset_$delayMillis"
    )
    Box(
        modifier = Modifier
            .alpha(alpha)
            .offset { IntOffset(0, offsetY.roundToInt()) }
    ) {
        content()
    }
}

@Composable
fun DashboardAppBar(
    modifier: Modifier = Modifier,
    walletBalance: Double = 0.0,
    onMonthSelect: (String) -> Unit,
    onLogout: () -> Unit = {}
) {
    val formattedBalance = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(walletBalance)

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Surface(modifier = modifier) {
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
            // Row 1: Title (delay 0) + Logout (delay 80)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppBarItem(delayMillis = 0, visible = visible) {
                    Text(
                        text = "Dompet Ibu",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        style = TextStyle(shadow = CommonTextStyle().textShadow())
                    )
                }
                AppBarItem(delayMillis = 80, visible = visible) {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                }
            }

            // Row 2: Month dropdown (delay 160)
            AppBarItem(delayMillis = 160, visible = visible) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    MonthDropdown(onSelect = { onMonthSelect(it) })
                }
            }

            // Wallet section (delay 240)
            AppBarItem(delayMillis = 240, visible = visible) {
                DashboardAppBarWallet(formattedBalance)
            }
        }
    }
}

@Composable
fun DashboardAppBarWallet(formattedBalance: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 20.dp)
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