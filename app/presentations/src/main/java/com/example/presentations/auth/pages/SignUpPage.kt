package com.example.presentations.auth.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.components.CustomOutlineTextField
import com.example.core.components.ValidationResult
import com.example.core.components.Validators
import com.example.core.components.animations.SlideAnimationTransition
import com.example.presentations.auth.viewmodel.SignUpViewModel
import com.example.presentations.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = koinViewModel(),
    onLoginClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // 1. Logo — delay 0ms
            SlideAnimationTransition(visible = visible, delayMillis = 0) {
                Image(
                    painter = painterResource(id = R.drawable.picsvg_black),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(90.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 2. "Dompet Ibu" — delay 80ms
            SlideAnimationTransition(visible = visible, delayMillis = 80) {
                Text(
                    text = "Dompet Ibu",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(bottom = 16.dp))
            }

            // 3. Email field — delay 160ms
            SlideAnimationTransition(visible = visible, delayMillis = 160) {
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
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Password field — delay 240ms
            SlideAnimationTransition(visible = visible, delayMillis = 240) {
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
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 5. Confirm password — delay 320ms
            SlideAnimationTransition(visible = visible, delayMillis = 320) {
                CustomOutlineTextField(
                    value = state.repassword,
                    onValueChange = { viewModel.onRepasswordChange(it) },
                    label = "Confirm Password",
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm Password Icon") },
                    validators = listOf(
                        { Validators.isNotEmpty(it) },
                        {
                            ValidationResult(
                                isValid = it == state.password,
                                errorMessage = if (it == state.password) null else "Passwords do not match"
                            )
                        }
                    ),
                    isPasswordField = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 6. Sign Up button — delay 400ms
            SlideAnimationTransition(visible = visible, delayMillis = 400) {
                Button(
                    onClick = { viewModel.signUp() },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isLoading && state.isFormValid
                ) {
                    Text("Sign Up")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 7. Login link — delay 480ms
            SlideAnimationTransition(visible = visible, delayMillis = 480) {
                Text(
                    text = "Sudah punya akun? Login",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onLoginClick() }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}