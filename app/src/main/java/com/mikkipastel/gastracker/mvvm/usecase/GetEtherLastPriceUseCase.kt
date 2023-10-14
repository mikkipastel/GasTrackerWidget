package com.mikkipastel.gastracker.mvvm.usecase

import com.mikkipastel.gastracker.mvvm.model.EtherPrice
import com.mikkipastel.gastracker.mvvm.model.SuccessResponse
import com.mikkipastel.gastracker.mvvm.repository.GasTrackerRepository
import org.koin.java.KoinJavaComponent.getKoin

class GetEtherLastPriceUseCase(
    private val repository: Lazy<GasTrackerRepository> = getKoin().inject()
) {
    suspend fun invoke(): SuccessResponse<EtherPrice> {
        return repository.value.getEtherLastPrice()
    }
}