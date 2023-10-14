package com.mikkipastel.gastracker.mvvm.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GasOracle(
    @SerializedName("LastBlock") val lastBlock: String?,
    @SerializedName("SafeGasPrice") val lowGasPrice: String?,
    @SerializedName("ProposeGasPrice") val averageGasPrice: String?,
    @SerializedName("FastGasPrice") val highGasPrice: String?,
    @SerializedName("suggestBaseFee") val suggestBaseFee: String?,
    @SerializedName("gasUsedRatio") val gasUsedRatio: String?,
): Parcelable
