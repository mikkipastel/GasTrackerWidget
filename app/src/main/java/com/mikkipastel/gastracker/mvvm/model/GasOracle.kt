package com.mikkipastel.gastracker.mvvm.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GasOracle(
    @SerializedName("LastBlock") val lastBlock: String? = null,
    @SerializedName("SafeGasPrice") val lowGasPrice: String? = null,
    @SerializedName("ProposeGasPrice") val averageGasPrice: String? = null,
    @SerializedName("FastGasPrice") val highGasPrice: String? = null,
    @SerializedName("suggestBaseFee") val suggestBaseFee: String? = null,
    @SerializedName("gasUsedRatio") val gasUsedRatio: String? = null,
): Parcelable
