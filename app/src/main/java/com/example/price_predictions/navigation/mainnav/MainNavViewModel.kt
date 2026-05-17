package com.example.price_predictions.navigation.mainnav

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainNavViewModel: ViewModel(){
    val mainBackStack = mutableStateListOf<Any>(MainRoute.HomePage)

    var selectedMonth = mutableStateOf<String?>(null)
        private set

    fun updateSelectedMonth(month: String) {
        selectedMonth.value = month
    }

    fun navigateTo(route: Any) {
        if (mainBackStack.lastOrNull() != route) {
            mainBackStack.clear()
            mainBackStack.add(route)
        }
    }

    fun pop(){
        if(mainBackStack.size > 1){
            mainBackStack.removeAt(mainBackStack.size - 1)
            true
        }else{
            false
        }
    }
}