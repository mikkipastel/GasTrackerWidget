package com.mikkipastel.gastracker.mvvm.repository

import com.mikkipastel.gastracker.mvvm.manager.ApiService
import com.mikkipastel.gastracker.mvvm.model.EtherPrice
import com.mikkipastel.gastracker.mvvm.model.GasOracle
import com.mikkipastel.gastracker.mvvm.model.SuccessResponse

interface GasTrackerRepository {
    suspend fun getGasOracle(): SuccessResponse<GasOracle>
    suspend fun getEtherLastPrice(): SuccessResponse<EtherPrice>
}

class GasTrackerRepositoryImpl(
    private val service: ApiService
): GasTrackerRepository {
    override suspend fun getGasOracle(): SuccessResponse<GasOracle> {
        return service.getGasOracle()
    }

    override suspend fun getEtherLastPrice(): SuccessResponse<EtherPrice> {
        return service.getEtherLastPrice()
    }

}