package com.example.presentations.auth.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.components.CustomOutlineTextField
import com.example.core.components.Validators
import com.example.presentations.auth.viewmodel.SignUpViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = koinViewModel(),
    onLoginClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.error, state.success) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbarState()
        }
        state.success?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearSnackbarState()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(bottom = 16.dp))
            }

            CustomOutlineTextField(
                value = state.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = "Email",
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
                validators = listOf(
                    { Validators.isNotEmpty(it) },
                    { Validators.isValidEmail(it) }
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlineTextField(
                value = state.password,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = "Password",
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
                validators = listOf(
                    { Validators.isNotEmpty(it) },
                    { Validators.isValidPassword(it) }
                ),
                isPasswordField = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomOutlineTextField(
                value = state.repassword,
                onValueChange = { viewModel.onRepasswordChange(it) },
                label = "Confirm Password",
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm Password Icon") },
                validators = listOf(
                    { Validators.isNotEmpty(it) },
                    {
                        com.example.core.components.ValidationResult(
                            isValid = it == state.password,
                            errorMessage = if (it == state.password) null else "Passwords do not match"
                        )
                    }
                ),
                isPasswordField = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.signUp()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading && state.isFormValid
            ) {
                Text("Sign Up")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sudah punya akun? Login",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable { onLoginClick() }
            )
        }
    }
}