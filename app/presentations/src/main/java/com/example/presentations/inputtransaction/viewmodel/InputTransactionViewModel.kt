package com.example.presentations.inputtransaction.viewmodel

import androidx.lifecycle.ViewModel
import com.example.presentations.inputtransaction.state.InputTransactionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InputTransactionViewModel: ViewModel() {
    private val _state = MutableStateFlow(InputTransactionState())
    val state = _state.asStateFlow()


}