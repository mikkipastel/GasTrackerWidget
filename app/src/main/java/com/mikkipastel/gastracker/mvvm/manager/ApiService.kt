package com.mikkipastel.gastracker.mvvm.manager

import com.mikkipastel.gastracker.mvvm.model.EtherPrice
import com.mikkipastel.gastracker.mvvm.model.GasOracle
import com.mikkipastel.gastracker.mvvm.model.SuccessResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val apiKey = "231FNMTE7P324UWZ9PWUUTID4QERE9TBKZ"
interface ApiService {
    @GET("api")
    suspend fun getGasOracle(
        @Query("module") module: String = "gastracker",
        @Query("action") action: String = "gasoracle",
        @Query("apikey") apikey: String = apiKey,
    ): SuccessResponse<GasOracle>
    @GET("api")
    suspend fun getEtherLastPrice(
        @Query("module") module: String = "stats",
        @Query("action") action: String = "ethprice",
        @Query("apikey") apikey: String = apiKey,
    ): SuccessResponse<EtherPrice>
}