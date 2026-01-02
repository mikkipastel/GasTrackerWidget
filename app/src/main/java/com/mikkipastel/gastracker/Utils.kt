package com.mikkipastel.gastracker

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String?.convertTo2Decimal(): String = this?.toDouble().convertTo2Decimal()

fun Double?.convertTo2Decimal(): String = "%.2f".format(this)

fun getCurrentTimeStamp(): String {
    val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.forLanguageTag("TH"))
    return simpleDate.format(Date())
}

fun calculateGasPriceUsd(ethPrice: String?, gasPrice: String?): String {
    val gasUnit = 21000
    val gasEthPrice = gasUnit.times(gasPrice?.toFloatSafe() ?: 0f) * 0.000000001
    val result = ethPrice?.toFloat()?.times(gasEthPrice)
    return result.convertTo2Decimal()
}

fun String?.toFloatSafe(): Float? {
    return this?.replace(",", ".")?.toFloatOrNull()
}