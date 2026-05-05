package com.example.presentations.auth.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.presentations.auth.viewmodel.LoginPageViewModel

@Composable
fun LoginPage(modifier: Modifier = Modifier,viewModel: LoginPageViewModel = viewModel()) {
    Scaffold() {
        Column(
            modifier = Modifier.padding(it), verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            TextField(
                value = "",
                onValueChange = {},
                label = { androidx.compose.material3.Text("Username") }
            )
            TextField(
                value = "",
                onValueChange = {},
                label = { androidx.compose.material3.Text("Password") }
            )
        }
    }

}