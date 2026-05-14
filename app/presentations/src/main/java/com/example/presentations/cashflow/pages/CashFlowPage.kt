package com.example.presentations.cashflow.pages

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import coil.compose.AsyncImage
import java.io.File
import com.example.core.components.CustomOutlineTextField
import com.example.core.components.CustomTextFieldDropDown
import com.example.core.components.TextFieldDate
import com.example.core.components.animations.SlideAnimationTransition
import com.example.core.constans.enums.InputTransactionEnum
import com.example.core.components.LocalSnackbarHostState
import com.example.presentations.cashflow.viewmodel.CashFlowViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: CashFlowViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = LocalSnackbarHostState.current
    val stateScroll = rememberScrollState()

    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    LaunchedEffect(state.success) {
        state.success?.let {
            snackbarHostState.showSnackbar(message = it)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.updateImageUri(uri)
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        TabRow(selectedTabIndex = state.currentTab) {
            InputTransactionEnum.TypeCashFlow.entries.forEachIndexed { index, title ->
                Tab(
                    selected = state.currentTab == index,
                    onClick = { viewModel.updateTabType(index) },
                    text = { Text(title.value) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))

        Column (modifier = Modifier.fillMaxSize().verticalScroll(stateScroll)) {
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
                            value = state.amount,
                            onValueChange = { viewModel.updateAmount(it) },
                            label = "Amount",
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
                            value = state.date,
                            onValueChange = { viewModel.updateDate(it) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SlideAnimationTransition(visible = true, delayMillis = 300) {
                        CustomOutlineTextField(
                            maxLines = 4,
                            value = state.description,
                            onValueChange = { viewModel.updateDescription(it) },
                            label = "Description",
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Info,
                                    contentDescription = "Description"
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    SlideAnimationTransition(visible = true, delayMillis = 400) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedButton(onClick = { galleryLauncher.launch("image/*") }) {
                                    Text(if (state.imageUri != null) "Ganti Gambar" else "Pilih Gambar")
                                }
                                if (state.imageUri != null) {
                                    Text(
                                        text = "Gambar Terpilih",
                                        color = MaterialTheme.colorScheme.primary,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                } else {
                                    Text(
                                        "Belum ada gambar",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }

                            if (state.imageUri != null) {
                                Spacer(modifier = Modifier.height(16.dp))
                                AsyncImage(
                                    model = state.imageUri,
                                    contentDescription = "Selected Image",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    SlideAnimationTransition(visible = true, delayMillis = 500) {
                        Button(
                            onClick = {
                                viewModel.submit { uri ->
                                    val inputStream = context.contentResolver.openInputStream(uri)
                                    val file = File(
                                        context.cacheDir,
                                        "temp_image_${System.currentTimeMillis()}.jpg"
                                    )
                                    inputStream?.use { input ->
                                        file.outputStream().use { output ->
                                            input.copyTo(output)
                                        }
                                    }
                                    file.takeIf { it.exists() }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !state.isLoading
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Text("Save Transaction")
                            }
                        }
                    }
                }
            }
        }
    }
}