package com.mikkipastel.gastracker

import android.app.Application
import com.mikkipastel.gastracker.mvvm.manager.HttpManager
import com.mikkipastel.gastracker.mvvm.repository.GasTrackerRepository
import com.mikkipastel.gastracker.mvvm.repository.GasTrackerRepositoryImpl
import com.mikkipastel.gastracker.mvvm.usecase.GetEtherLastPriceUseCase
import com.mikkipastel.gastracker.mvvm.usecase.GetGasOracleUseCase
import com.mikkipastel.gastracker.mvvm.viewmodel.GasTrackerViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)

            val module = module {
                single { HttpManager().getApiService() }
                single<GasTrackerRepository> { GasTrackerRepositoryImpl(get()) }
                factory { GetGasOracleUseCase() }
                factory { GetEtherLastPriceUseCase() }
                viewModel { GasTrackerViewModel() }
            }

            modules(listOf(module))
        }
    }
}