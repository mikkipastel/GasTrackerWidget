package com.mikkipastel.gastracker.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
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
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.mikkipastel.gastracker.MainActivity
import com.mikkipastel.gastracker.R

class GasTrackerWidget : GlanceAppWidget() {

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
            widgetLargeSize()
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
                    textGweiPrice(context.getString(R.string.text_usd, "0.12"))
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
                    textGweiPrice(context.getString(R.string.text_usd, "0.12"))
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
                    textGweiPrice(context.getString(R.string.text_usd, "0.12"))
                }
            }
            Text(
                text = "updated $timestamp",
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 8.sp,
                    textAlign = TextAlign.End
                ),
                modifier = GlanceModifier.fillMaxWidth()
            )
        }
    }

    @Composable
    private fun etherPriceView(context: Context, ethusd: String?) {
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
                modifier = GlanceModifier.width(32.dp).height(32.dp)
            )
            Text(
                text = context.getString(R.string.text_usd, ethusd),
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 24.sp,
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
    private fun spacerWidth8dp() {
        Spacer(
            modifier = GlanceModifier.width(8.dp)
        )
    }
}