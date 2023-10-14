package com.mikkipastel.gastracker.mvvm.model

import com.google.gson.annotations.SerializedName

class SuccessResponse<out T>(
    @SerializedName("status") val status: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("result") val result: T?
)
