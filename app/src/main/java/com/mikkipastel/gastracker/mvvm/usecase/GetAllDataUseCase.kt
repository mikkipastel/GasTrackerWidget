package com.mikkipastel.gastracker.mvvm.usecase

import com.mikkipastel.gastracker.calculateGasPriceUsd
import com.mikkipastel.gastracker.convertTo2Decimal

class GetAllDataUseCase(
    private val getEtherLastPriceUseCase: GetEtherLastPriceUseCase = GetEtherLastPriceUseCase(),
    private val getGasOracleUseCase: GetGasOracleUseCase = GetGasOracleUseCase(),
) {
    suspend fun invoke(): GasTrackerData {
        val getEtherLastPriceData = getEtherLastPriceUseCase.invoke().result
        val etherPrice = getEtherLastPriceData?.ethusd.convertTo2Decimal()

        val getGasOracleData = getGasOracleUseCase.invoke().result
        val lowGasGwei = getGasOracleData?.lowGasPrice.convertTo2Decimal()
        val averageGasGwei = getGasOracleData?.averageGasPrice.convertTo2Decimal()
        val highGasGwei = getGasOracleData?.highGasPrice.convertTo2Decimal()
        val lowGasPrice = calculateGasPriceUsd(etherPrice, lowGasGwei)
        val avgGasPrice = calculateGasPriceUsd(etherPrice, averageGasGwei)
        val highGasPrice = calculateGasPriceUsd(etherPrice, highGasGwei)

        return GasTrackerData(
            etherPrice,
            lowGasGwei,
            averageGasGwei,
            highGasGwei,
            lowGasPrice,
            avgGasPrice,
            highGasPrice
        )
    }
}

data class GasTrackerData(
    val ethusd: String? = null,
    val lowGasGwei: String? = null,
    val averageGasGwei: String? = null,
    val highGasGwei: String? = null,
    val lowGasPrice: String? = null,
    val averageGasPrice: String? = null,
    val highGasPrice: String? = null,
)