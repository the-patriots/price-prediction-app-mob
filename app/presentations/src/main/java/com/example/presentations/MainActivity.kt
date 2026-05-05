package com.example.presentations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TestOnlsy(modifier: Modifier = Modifier) {
    Scaffold() {
        Column(modifier = Modifier.padding(it)) {
            Text("Test")
        }
    }
}