package com.mikkipastel.gastracker.mvvm.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EtherPrice(
    @SerializedName("ethbtc") val ethbtc: String? = null,
    @SerializedName("ethbtc_timestamp") val ethbtcTimestamp: String? = null,
    @SerializedName("ethusd") val ethusd: String? = null,
    @SerializedName("ethusd_timestamp") val ethusdTimestamp: String? = null,
): Parcelable
