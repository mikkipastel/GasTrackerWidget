package com.mikkipastel.gastracker

import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String?.convertTo2Decimal(): String = String.format("%.2f", this?.toFloat())

fun getCurrentTimeStamp(): String {
    val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.forLanguageTag("TH"))
    return simpleDate.format(Date())
}

fun calculateGasPriceUsd(ethPrice: String?, gasPrice: String?): String {
    val gasUnit = 21000
    val gasEthPrice = BigDecimal(gasUnit * (gasPrice?.toInt() ?: 0)) * BigDecimal(0.000000001)
    val result = (BigDecimal(ethPrice) * gasEthPrice)
    return String.format("%.2f", result.toFloat())
}