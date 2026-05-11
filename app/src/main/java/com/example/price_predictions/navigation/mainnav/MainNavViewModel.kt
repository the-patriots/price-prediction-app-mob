package com.example.price_predictions.navigation.mainnav

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainNavViewModel: ViewModel(){
    val mainBackStack = mutableStateListOf<MainRoute>(MainRoute.HomePage)

    fun pop(){
        if(mainBackStack.size > 1){
            mainBackStack.removeAt(mainBackStack.size - 1)
            true
        }else{
            false
        }
    }
}