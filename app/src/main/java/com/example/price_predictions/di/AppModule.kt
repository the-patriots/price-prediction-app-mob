package com.example.price_predictions.di

import androidx.room.Room
import com.example.data.AppDatabase
import com.example.presentations.home.viemodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module


fun appModule() = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database").build()
    }

   viewModel { HomeViewModel() }
}