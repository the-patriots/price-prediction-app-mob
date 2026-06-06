package com.example.price_predictions.di

import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.auth.datasource.AuthNetworkDatasource
import com.example.data.auth.datasource.AuthNetworkDatasourceImpl
import com.example.data.auth.repository.AuthRepositoryImpl
import com.example.domain.auth.repository.AuthRepository
import com.example.domain.auth.usecases.LoginUseCase
import com.example.domain.auth.usecases.SignUpUseCase
import com.example.domain.cashflow.repository.CashFlowRepository
import com.example.domain.cashflow.usecases.CheckAiPriceUseCase
import com.example.domain.cashflow.usecases.CreateCashFlowUseCase
import com.example.domain.cashflow.usecases.DeleteCashFlowUseCase
import com.example.data.cashflow.datasource.CashFlowNetworkDatasource
import com.example.data.cashflow.datasource.CashFLowNetworkDatasourceImpl
import com.example.data.cashflow.repository.CashFlowRepositoryImpl
import com.example.data.analytic.datasource.AnalyticNetworkDatasource
import com.example.data.analytic.datasource.AnalyticNetworkDatasourceImpl
import com.example.data.analytic.repository.AnalyticRepositoryImpl
import com.example.data.home.datasource.HomeNetworkDatasource
import com.example.data.home.datasource.HomeNetworkDatasourceImpl
import com.example.data.home.repository.HomeRepositoryImpl
import com.example.domain.analytic.repository.AnalyticRepository
import com.example.domain.analytic.usecases.GetAnalyticDataUseCase
import com.example.domain.cashflow.usecases.GetCashFlowsUseCase
import com.example.domain.home.repository.HomeRepository
import com.example.domain.home.usecases.GetHomeSummaryUseCase
import com.example.domain.home.usecases.GetRecentCashFlowsUseCase
import com.example.presentations.auth.viewmodel.LoginPageViewModel
import com.example.presentations.auth.viewmodel.SignUpViewModel
import com.example.presentations.home.viemodel.HomeViewModel
import com.example.presentations.cashflow.viewmodel.CashFlowViewModel
import com.example.presentations.analytic.viewmodel.AnalyticViewModel
import com.example.price_predictions.navigation.AppNavViewModel
import com.example.price_predictions.navigation.authnav.AuthNavViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.functions.Functions
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import com.example.price_predictions.BuildConfig
import com.example.price_predictions.navigation.mainnav.MainNavViewModel
import io.github.jan.supabase.annotations.SupabaseInternal
import io.ktor.client.plugins.HttpTimeout
import org.koin.core.module.dsl.singleOf

import org.koin.dsl.module


@OptIn(SupabaseInternal::class)
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
            install(Functions)
            httpConfig {
                install(HttpTimeout) {
                    requestTimeoutMillis = 60_000L // Maksimal waktu tunggu seluruh request (60 detik)
                    connectTimeoutMillis = 60_000L // Maksimal waktu tunggu untuk terhubung ke server
                    socketTimeoutMillis = 60_000L  // Maksimal waktu jeda antar paket data yang diterima
                }
            }
        }

    }

    //home
    single<HomeNetworkDatasource> { HomeNetworkDatasourceImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    factory { GetHomeSummaryUseCase(get()) }
    factory { GetRecentCashFlowsUseCase(get()) }
    viewModel { HomeViewModel(get(), get()) }


    //navigation app
    viewModel { AppNavViewModel(get()) }

    //navigation auth
    viewModel { AuthNavViewModel() }

    //navigation main
    viewModel { MainNavViewModel() }

    //Auth
    single <AuthNetworkDatasource>{ AuthNetworkDatasourceImpl(get()) }
    single <AuthRepository>{ AuthRepositoryImpl(get()) }
    single { LoginUseCase(get()) }
    single { SignUpUseCase(get()) }
    viewModel { LoginPageViewModel(get()) }
    viewModel { SignUpViewModel(get()) }

    //cashflow
    single<CashFlowNetworkDatasource> { CashFLowNetworkDatasourceImpl(get()) }
    single<CashFlowRepository> { CashFlowRepositoryImpl(get()) }
    factory { CreateCashFlowUseCase(get()) }
    factory { CheckAiPriceUseCase(get()) }
    factory { GetCashFlowsUseCase(get()) }
    factory { DeleteCashFlowUseCase(get()) }
    viewModel { CashFlowViewModel(get(), get(), get(), get()) }

    //analytic
    single<AnalyticNetworkDatasource> { AnalyticNetworkDatasourceImpl(get()) }
    single<AnalyticRepository> { AnalyticRepositoryImpl(get()) }
    single { GetAnalyticDataUseCase(get()) }
    viewModel { AnalyticViewModel(get()) }
}