package com.mikkipastel.gastracker.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.mikkipastel.gastracker.MainActivity
import com.mikkipastel.gastracker.R
import com.mikkipastel.gastracker.calculateGasPriceUsd

class GasTrackerWidget : GlanceAppWidget() {

    companion object {
        private val SMALL_RECTANGLE = DpSize(130.dp, 100.dp)
        private val MEDIUM_SQUARE = DpSize(130.dp, 220.dp)
        private val LARGE_RECTANGLE = DpSize(276.dp, 220.dp)
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(
            SMALL_RECTANGLE,
            MEDIUM_SQUARE,
            LARGE_RECTANGLE
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            setGasTrackerWidget()
        }
    }


    @Preview (showBackground = true)
    @Composable
    private fun setGasTrackerWidget() {
        GlanceTheme {
            when (LocalSize.current) {
                SMALL_RECTANGLE -> widgetSmallSize()
                MEDIUM_SQUARE -> widgetMediumSize()
                LARGE_RECTANGLE -> widgetLargeSize()
            }
        }
    }

    @Composable
    private fun widgetSmallSize() {
        val context = LocalContext.current
        val prefs = currentState<Preferences>()

        val ethusd = prefs[GasTrackerWidgetReceiver.ethusd]
        val lowGasPrice = prefs[GasTrackerWidgetReceiver.lowGasPrice]
        val averageGasPrice = prefs[GasTrackerWidgetReceiver.averageGasPrice]
        val highGasPrice = prefs[GasTrackerWidgetReceiver.highGasPrice]

        Column(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(8.dp)
                .clickable {
                    actionStartActivity<MainActivity>()
                }
        ) {
            etherPriceView(context, ethusd)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp, 0.dp, 8.dp)
            ) {
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsSuccess)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    textGweiPrice(lowGasPrice.toString())
                }
                spacerWidth8dp()
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsPrimary)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    textGweiPrice(averageGasPrice.toString())
                }
                spacerWidth8dp()
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsDanger)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    textGweiPrice(highGasPrice.toString())
                }
            }
        }
    }

    @Composable
    private fun widgetMediumSize() {
        val context = LocalContext.current
        val prefs = currentState<Preferences>()

        val ethusd = prefs[GasTrackerWidgetReceiver.ethusd]
        val lowGasPrice = prefs[GasTrackerWidgetReceiver.lowGasPrice]
        val averageGasPrice = prefs[GasTrackerWidgetReceiver.averageGasPrice]
        val highGasPrice = prefs[GasTrackerWidgetReceiver.highGasPrice]

        Column(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(8.dp)
                .clickable {
                    actionStartActivity<MainActivity>()
                }
        ) {
            etherPriceView(context, ethusd)
            Column(
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp, 0.dp, 8.dp)
            ) {
                Row(
                    modifier = GlanceModifier
                        .background(R.color.bsSuccess)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .fillMaxWidth()
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    textEmojiHeader("🐢")
                    textGweiAndPrice(
                        context.getString(R.string.text_gwei, lowGasPrice),
                        context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, lowGasPrice))
                    )
                }
                spacerHeight8dp()
                Row(
                    modifier = GlanceModifier
                        .background(R.color.bsPrimary)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .fillMaxWidth()
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    textEmojiHeader("🚶")
                    textGweiAndPrice(
                        context.getString(R.string.text_gwei, averageGasPrice),
                        context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, averageGasPrice))
                    )
                }
                spacerHeight8dp()
                Row(
                    modifier = GlanceModifier
                        .background(R.color.bsDanger)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .fillMaxWidth()
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    textEmojiHeader("⚡️")
                    textGweiAndPrice(
                        context.getString(R.string.text_gwei, highGasPrice),
                        context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, highGasPrice))
                    )
                }
            }
        }
    }

    @Composable
    private fun widgetLargeSize() {
        val context = LocalContext.current
        val prefs = currentState<Preferences>()

        val ethusd = prefs[GasTrackerWidgetReceiver.ethusd]
        val timestamp = prefs[GasTrackerWidgetReceiver.timestamp]
        val lowGasPrice = prefs[GasTrackerWidgetReceiver.lowGasPrice]
        val averageGasPrice = prefs[GasTrackerWidgetReceiver.averageGasPrice]
        val highGasPrice = prefs[GasTrackerWidgetReceiver.highGasPrice]

        Column(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(8.dp)
                .clickable {
                    actionStartActivity<MainActivity>()
                }
        ) {
            etherPriceView(context, ethusd)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp, 0.dp, 8.dp)
            ) {
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsSuccess)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    textEmojiHeader("🐢")
                    textLabelHeader(context.getString(R.string.text_gas_low))
                    textGwei(context.getString(R.string.text_gwei, lowGasPrice))
                    textGweiPrice(context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, lowGasPrice)))
                }
                spacerWidth8dp()
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsPrimary)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    textEmojiHeader("🚶")
                    textLabelHeader(context.getString(R.string.text_gas_avg))
                    textGwei(context.getString(R.string.text_gwei, averageGasPrice))
                    textGweiPrice(context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, averageGasPrice)))
                }
                spacerWidth8dp()
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsDanger)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    textEmojiHeader("⚡️")
                    textLabelHeader(context.getString(R.string.text_gas_high))
                    textGwei(context.getString(R.string.text_gwei, highGasPrice))
                    textGweiPrice(context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, highGasPrice)))
                }
            }
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "updated $timestamp",
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontSize = 8.sp
                    )
                )
                Image(
                    provider = ImageProvider(R.drawable.ic_refresh),
                    contentDescription = "refresh",
                    modifier = GlanceModifier.clickable {
                        // update data
                        actionRunCallback<GasTrackerCallback>()
                    }
                )
            }
        }
    }

    @Composable
    private fun etherPriceView(
        context: Context,
        ethusd: String?
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp)
                .cornerRadius(16.dp)
        ) {
            Image(
                provider = ImageProvider(R.drawable.ic_eth_diamond_purple),
                contentDescription = "ETH logo",
                modifier = GlanceModifier.width(24.dp).height(24.dp)
            )
            Text(
                text = context.getString(R.string.text_usd, ethusd),
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

    @Composable
    private fun textEmojiHeader(emoji: String) {
        Text(
            text = emoji,
            style = TextStyle(
                fontSize = 24.sp
            )
        )
    }

    @Composable
    private fun textLabelHeader(label: String) {
        Text(
            text = label,
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }

    @Composable
    private fun textGwei(gwei: String) {
        Text(
            text = gwei,
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 20.sp
            )
        )
    }

    @Composable
    private fun textGweiPrice(price: String) {
        Text(
            text = price,
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 16.sp
            )
        )
    }

    @Composable
    private fun textGweiAndPrice(gwei: String, price: String) {
        Text(
            text = "$gwei ($price)",
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 16.sp
            )
        )
    }

    @Composable
    private fun spacerWidth8dp() {
        Spacer(
            modifier = GlanceModifier.width(8.dp)
        )
    }

    @Composable
    private fun spacerHeight8dp() {
        Spacer(
            modifier = GlanceModifier.height(8.dp)
        )
    }
}