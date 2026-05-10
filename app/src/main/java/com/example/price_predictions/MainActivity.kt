package com.example.price_predictions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.core.ui.theme.Price_predictionsTheme
import com.example.price_predictions.di.appModule
import com.example.price_predictions.navigation.AppNav
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startKoin {
            modules(appModule())
        }

        setContent {
            Price_predictionsTheme {
                AppNav()
            }
        }
    }
}