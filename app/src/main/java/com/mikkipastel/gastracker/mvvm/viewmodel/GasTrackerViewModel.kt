package com.mikkipastel.gastracker.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikkipastel.gastracker.mvvm.model.EtherPrice
import com.mikkipastel.gastracker.mvvm.model.GasOracle
import com.mikkipastel.gastracker.mvvm.usecase.GetEtherLastPriceUseCase
import com.mikkipastel.gastracker.mvvm.usecase.GetGasOracleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GasTrackerViewModel(
    private val getGasOracleUseCase: GetGasOracleUseCase = GetGasOracleUseCase(),
    private val getEtherLastPriceUseCase: GetEtherLastPriceUseCase = GetEtherLastPriceUseCase()
): ViewModel() {

    private var _gasOracle = MutableStateFlow(GasOracle())
    val gasOracle: StateFlow<GasOracle> = _gasOracle.asStateFlow()

    private var _etherPrice = MutableStateFlow(EtherPrice())
    val etherPrice: StateFlow<EtherPrice> = _etherPrice.asStateFlow()

    init {
        getGasOracle()
        getEtherLastPrice()
    }

    fun getGasOracle() = viewModelScope.launch {
        val response = getGasOracleUseCase.invoke()
        _gasOracle.value = response.result ?: GasOracle()
    }

    fun getEtherLastPrice() = viewModelScope.launch {
        val response = getEtherLastPriceUseCase.invoke()
        _etherPrice.value = response.result ?: EtherPrice()
    }
}