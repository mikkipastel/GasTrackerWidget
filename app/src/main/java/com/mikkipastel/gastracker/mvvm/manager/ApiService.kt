package com.mikkipastel.gastracker.mvvm.manager

import com.mikkipastel.gastracker.BuildConfig
import com.mikkipastel.gastracker.mvvm.model.EtherPrice
import com.mikkipastel.gastracker.mvvm.model.GasOracle
import com.mikkipastel.gastracker.mvvm.model.SuccessResponse
import retrofit2.http.GET
import retrofit2.http.Query

const val apiKey = BuildConfig.ETHERSCAN_API_KEY
const val ethereumChainId = "1"
interface ApiService {
    @GET("api")
    suspend fun getGasOracle(
        @Query("module") module: String = "gastracker",
        @Query("action") action: String = "gasoracle",
        @Query("apikey") apikey: String = apiKey,
        @Query("chainid") chainid: String = ethereumChainId
    ): SuccessResponse<GasOracle>
    @GET("api")
    suspend fun getEtherLastPrice(
        @Query("module") module: String = "stats",
        @Query("action") action: String = "ethprice",
        @Query("apikey") apikey: String = apiKey,
        @Query("chainid") chainid: String = ethereumChainId
    ): SuccessResponse<EtherPrice>
}