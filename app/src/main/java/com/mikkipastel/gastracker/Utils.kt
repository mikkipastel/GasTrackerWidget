package com.mikkipastel.gastracker

import java.text.SimpleDateFormat
import java.util.Date

fun String?.convertTo2Decimal(): String = String.format("%.2f", this?.toFloat())

fun getCurrentTimeStamp(): String {
    val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    return simpleDate.format(Date())
}