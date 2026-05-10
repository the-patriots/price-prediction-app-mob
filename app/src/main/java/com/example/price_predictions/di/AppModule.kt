package com.example.price_predictions.di

import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.auth.datasource.AuthNetworkDatasource
import com.example.data.auth.datasource.AuthNetworkDatasourceImpl
import com.example.data.auth.repository.AuthRepositoryImpl
import com.example.domain.auth.repository.AuthRepository
import com.example.domain.auth.usecases.LoginUseCase
import com.example.domain.auth.usecases.SignUpUseCase
import com.example.presentations.auth.viewmodel.LoginPageViewModel
import com.example.presentations.auth.viewmodel.SignUpViewModel
import com.example.presentations.home.viemodel.HomeViewModel
import com.example.price_predictions.navigation.AppNavigationState
import com.example.price_predictions.navigation.authnav.AuthNavViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.price_predictions.BuildConfig

import org.koin.dsl.module


fun appModule() = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    single {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_ANON_KEY
        ) {
            install(Auth)
            install(Postgrest)
        }
    }

    viewModel { HomeViewModel() }


    //navigation auth
    single { AppNavigationState() }
    viewModel { AuthNavViewModel() }

    //Auth
    single <AuthNetworkDatasource>{ AuthNetworkDatasourceImpl(get()) }
    single <AuthRepository>{ AuthRepositoryImpl(get()) }
    single { LoginUseCase(get()) }
    single { SignUpUseCase(get()) }
    viewModel { LoginPageViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
}