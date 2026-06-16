package com.example.presentations.cashflow.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.ui.graphics.Color
import com.example.core.components.CustomOutlineTextField
import com.example.core.components.CustomTextFieldDropDown
import com.example.core.components.TextFieldDate
import com.example.core.components.animations.SlideAnimationTransition
import com.example.core.constans.enums.InputTransactionEnum
import com.example.core.components.LocalSnackbarHostState
import com.example.core.ui.theme.PrimaryBlue
import com.example.presentations.cashflow.viewmodel.CashFlowViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: CashFlowViewModel = koinViewModel(),
    id: String? = null,
    onSubmit: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current
    val stateScroll = rememberScrollState()
    val isEditForm = remember { !id.isNullOrBlank() }


    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(message = it)
            viewModel.resetSnackbar()

        }
    }

    LaunchedEffect(state.success) {
        state.success?.let {
            snackbarHostState.showSnackbar(message = it)
            viewModel.resetSnackbar()
        }
    }

    if (state.showAiDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.cancelSave() },
            title = { Text(text = "Analisis Harga") },
            text = { Text(text = state.aiResultText) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.confirmSave(id)
                    onSubmit()
                }) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cancelSave() }) {
                    Text("Batal")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
       if (!isEditForm) TabRow(
            selectedTabIndex = state.currentTab,
            contentColor = PrimaryBlue,
            indicator = { tabPositions ->
                TabRowDefaults.PrimaryIndicator(
                    color = PrimaryBlue,
                    modifier = Modifier.tabIndicatorOffset(tabPositions[state.currentTab])
                )
            }
        ) {
            InputTransactionEnum.TypeCashFlow.entries.forEachIndexed { index, title ->
                Tab(
                    selected = state.currentTab == index,
                    onClick = { viewModel.updateTabType(index) },
                    text = { Text(title.value) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(stateScroll)) {
            key(state.currentTab) {
                Column {
                    SlideAnimationTransition(visible = true, delayMillis = 0) {
                        CustomTextFieldDropDown(
                            defaultItemSelected = state.selectedCategory,
                            listItem = if (state.currentTab == 0) state.pengeluaranItems else state.pemasukanItems,
                            onItemSelected = {
                                viewModel.updateCategory(it)
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SlideAnimationTransition(visible = true, delayMillis = 100) {
                        CustomOutlineTextField(
                            value = state.amountString,
                            onValueChange = { viewModel.updateAmount(it) },
                            label = "Jumlah",
                            leadingIcon = {
                                Text(
                                    "Rp",
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SlideAnimationTransition(visible = true, delayMillis = 200) {
                        TextFieldDate(
                            value = state.dateString,
                            onValueChange = { viewModel.updateDate(it) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SlideAnimationTransition(visible = true, delayMillis = 300) {
                        CustomOutlineTextField(
                            maxLines = 4,
                            value = state.payload.cashFlow.description ?: "",
                            onValueChange = { viewModel.updateDescription(it) },
                            label = "Barang",
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Description"
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    SlideAnimationTransition(visible = true, delayMillis = 500) {
                        Button(
                            onClick = {
                                viewModel.submit(id)
                                onSubmit()
                            },
                            colors = ButtonColors(
                                containerColor = PrimaryBlue,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.DarkGray
                            ),
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !state.isLoading && !state.isCheckingAi
                        ) {
                            if (state.isLoading || state.isCheckingAi) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text(text = if (isEditForm) "Edit Transaksi" else "Simpan Transaksi")
                            }
                        }
                    }
                }
            }
        }
    }
}