package com.mikkipastel.gastracker.mvvm.usecase

import com.mikkipastel.gastracker.mvvm.model.GasOracle
import com.mikkipastel.gastracker.mvvm.model.SuccessResponse
import com.mikkipastel.gastracker.mvvm.repository.GasTrackerRepository
import org.koin.java.KoinJavaComponent.getKoin

class GetGasOracleUseCase(
    private val repository: Lazy<GasTrackerRepository> = getKoin().inject()
) {
    suspend fun invoke(): SuccessResponse<GasOracle> {
        return repository.value.getGasOracle()
    }
}