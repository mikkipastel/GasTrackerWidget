package com.mikkipastel.gastracker.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikkipastel.gastracker.mvvm.model.EtherPrice
import com.mikkipastel.gastracker.mvvm.model.GasOracle
import com.mikkipastel.gastracker.mvvm.usecase.GetEtherLastPriceUseCase
import com.mikkipastel.gastracker.mvvm.usecase.GetGasOracleUseCase
import kotlinx.coroutines.launch

class GasTrackerViewModel(
    private val getGasOracleUseCase: GetGasOracleUseCase = GetGasOracleUseCase(),
    private val getEtherLastPriceUseCase: GetEtherLastPriceUseCase = GetEtherLastPriceUseCase()
): ViewModel() {

    private var _gasOracle = MutableLiveData<GasOracle?>()
    val gasOracle = MutableLiveData<GasOracle?>()

    private var _etherPrice = MutableLiveData<EtherPrice?>()
    val etherPrice = _etherPrice

    fun getGasOracle() = viewModelScope.launch {
        val response = getGasOracleUseCase.invoke()
        _gasOracle.value = response.result
    }

    fun getEtherLastPrice() = viewModelScope.launch {
        val response = getEtherLastPriceUseCase.invoke()
        _etherPrice.value = response.result
    }
}