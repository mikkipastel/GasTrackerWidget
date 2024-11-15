package com.mikkipastel.gastracker.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.mikkipastel.gastracker.convertTo2Decimal
import com.mikkipastel.gastracker.getCurrentTimeStamp
import com.mikkipastel.gastracker.mvvm.usecase.GetEtherLastPriceUseCase
import com.mikkipastel.gastracker.mvvm.usecase.GetGasOracleUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GasTrackerWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = GasTrackerWidget()

    private val getEtherLastPriceUseCase = GetEtherLastPriceUseCase()
    private val getGasOracleUseCase = GetGasOracleUseCase()

    companion object {
        val ethusd = stringPreferencesKey("ethusd")
        val timestamp = stringPreferencesKey("timestamp")
        val lowGasPrice = stringPreferencesKey("lowGasPrice")
        val averageGasPrice = stringPreferencesKey("averageGasPrice")
        val highGasPrice = stringPreferencesKey("highGasPrice")
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        observeData(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == GasTrackerCallback.UPDATE_ACTION) {
            observeData(context)
        }
    }

    private fun observeData(context: Context) {
        val coroutineScope = CoroutineScope(Dispatchers.Main)

        coroutineScope.launch {
            val etherPriceResult = getEtherLastPriceUseCase.invoke().result
            val gasOracleResult = getGasOracleUseCase.invoke().result

            val glanceId = GlanceAppWidgetManager(context).getGlanceIds(GasTrackerWidget::class.java).firstOrNull()

            glanceId?.let {
                updateAppWidgetState(context, PreferencesGlanceStateDefinition, it) { pref ->
                    pref.toMutablePreferences().apply {
                        this[ethusd] = etherPriceResult?.ethusd.convertTo2Decimal()
                        this[timestamp] = getCurrentTimeStamp()
                        this[lowGasPrice] = gasOracleResult?.lowGasPrice.convertTo2Decimal()
                        this[averageGasPrice] = gasOracleResult?.averageGasPrice.convertTo2Decimal()
                        this[highGasPrice] = gasOracleResult?.highGasPrice.convertTo2Decimal()
                    }
                }
                glanceAppWidget.update(context, it)
            }
        }
    }
}