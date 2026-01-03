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
        private val MEDIUM_SQUARE = DpSize(130.dp, 180.dp)
        private val LARGE_RECTANGLE = DpSize(276.dp, 180.dp)
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
            SetGasTrackerWidget()
        }
    }

    override suspend fun providePreview(context: Context, widgetCategory: Int) {
        provideContent { SetGasTrackerWidget() }
    }

    @Composable
    private fun SetGasTrackerWidget() {
        GlanceTheme {
            val context = LocalContext.current
            val prefs = currentState<Preferences>()

            val ethusd = prefs[GasTrackerWidgetReceiver.ethusd]
            val timestamp = prefs[GasTrackerWidgetReceiver.timestamp]
            val lowGasPrice = prefs[GasTrackerWidgetReceiver.lowGasPrice]
            val averageGasPrice = prefs[GasTrackerWidgetReceiver.averageGasPrice]
            val highGasPrice = prefs[GasTrackerWidgetReceiver.highGasPrice]

            when (LocalSize.current) {
                SMALL_RECTANGLE -> WidgetSmallSize(
                    context,
                    ethusd,
                    lowGasPrice,
                    averageGasPrice,
                    highGasPrice
                )
                MEDIUM_SQUARE -> WidgetMediumSize(
                    context,
                    ethusd,
                    lowGasPrice,
                    averageGasPrice,
                    highGasPrice
                )
                LARGE_RECTANGLE -> WidgetLargeSize(
                    context,
                    ethusd,
                    timestamp,
                    lowGasPrice,
                    averageGasPrice,
                    highGasPrice
                )
            }
        }
    }

    @Composable
    private fun WidgetSmallSize(
        context: Context,
        ethusd: String?,
        lowGasPrice: String?,
        averageGasPrice: String?,
        highGasPrice: String?
    ) {
        Column(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(8.dp)
                .clickable(actionStartActivity<MainActivity>())
        ) {
            EtherPriceView(context, ethusd)
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
                    TextGweiPrice(lowGasPrice)
                }
                SpacerWidth8dp()
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsPrimary)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextGweiPrice(averageGasPrice)
                }
                SpacerWidth8dp()
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsDanger)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextGweiPrice(highGasPrice)
                }
            }
        }
    }

    @Composable
    private fun WidgetMediumSize(
        context: Context,
        ethusd: String?,
        lowGasPrice: String?,
        averageGasPrice: String?,
        highGasPrice: String?
    ) {
        Column(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(8.dp)
                .clickable(actionStartActivity<MainActivity>())
        ) {
            EtherPriceView(context, ethusd)
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
                    TextEmojiHeader(context.getString(R.string.emoji_gas_low))
                    TextGweiAndPrice(
                        context.getString(R.string.text_gwei, lowGasPrice),
                        context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, lowGasPrice))
                    )
                }
                SpacerHeight8dp()
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
                    TextEmojiHeader(context.getString(R.string.emoji_gas_avg))
                    TextGweiAndPrice(
                        context.getString(R.string.text_gwei, averageGasPrice),
                        context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, averageGasPrice))
                    )
                }
                SpacerHeight8dp()
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
                    TextEmojiHeader(context.getString(R.string.emoji_gas_high))
                    TextGweiAndPrice(
                        context.getString(R.string.text_gwei, highGasPrice),
                        context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, highGasPrice))
                    )
                }
            }
        }
    }

    @Composable
    private fun WidgetLargeSize(
        context: Context,
        ethusd: String?,
        timestamp: String?,
        lowGasPrice: String?,
        averageGasPrice: String?,
        highGasPrice: String?
    ) {
        Column(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.DarkGray)
                .padding(8.dp)
                .clickable(actionStartActivity<MainActivity>())
        ) {
            EtherPriceView(context, ethusd)
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
                    TextEmojiHeader(context.getString(R.string.emoji_gas_low))
                    TextLabelHeader(context.getString(R.string.text_gas_low))
                    TextGwei(context, lowGasPrice)
                    TextGweiPrice(context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, lowGasPrice)))
                }
                SpacerWidth8dp()
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsPrimary)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextEmojiHeader(context.getString(R.string.emoji_gas_avg))
                    TextLabelHeader(context.getString(R.string.text_gas_avg))
                    TextGwei(context, averageGasPrice)
                    TextGweiPrice(context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, averageGasPrice)))
                }
                SpacerWidth8dp()
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsDanger)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextEmojiHeader(context.getString(R.string.emoji_gas_high))
                    TextLabelHeader(context.getString(R.string.text_gas_high))
                    TextGwei(context, highGasPrice)
                    TextGweiPrice(context.getString(R.string.text_usd, calculateGasPriceUsd(ethusd, highGasPrice)))
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
                        fontSize = 10.sp
                    )
                )
                Image(
                    provider = ImageProvider(R.drawable.ic_refresh),
                    contentDescription = context.getString(R.string.refresh),
                    modifier = GlanceModifier.clickable(actionRunCallback<GasTrackerCallback>())
                        .width(20.dp).height(20.dp)
                )
            }
        }
    }

    @Composable
    private fun EtherPriceView(
        context: Context,
        ethusd: String?
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(4.dp)
                .cornerRadius(16.dp)
        ) {
            Image(
                provider = ImageProvider(R.drawable.ic_eth_diamond_purple),
                contentDescription = context.getString(R.string.logo_ethereum),
                modifier = GlanceModifier.width(20.dp).height(20.dp)
            )
            Text(
                text = context.getString(R.string.text_usd, ethusd),
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

    @Composable
    private fun TextEmojiHeader(emoji: String) {
        Text(
            text = emoji,
            style = TextStyle(
                fontSize = 18.sp
            )
        )
    }

    @Composable
    private fun TextLabelHeader(label: String) {
        Text(
            text = label,
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }

    @Composable
    private fun TextGwei(context: Context, gwei: String?) {
        gwei?.let {
            Text(
                text = context.getString(R.string.text_gwei, it),
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 16.sp
                ),
                maxLines = 1
            )
        }
    }

    @Composable
    private fun TextGweiPrice(price: String?) {
        price?.let {
            Text(
                text = it,
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 12.sp
                )
            )
        }
    }

    @Composable
    private fun TextGweiAndPrice(gwei: String, price: String) {
        Text(
            text = "$gwei ($price)",
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontSize = 10.sp
            )
        )
    }

    @Composable
    private fun SpacerWidth8dp() {
        Spacer(
            modifier = GlanceModifier.width(8.dp)
        )
    }

    @Composable
    private fun SpacerHeight8dp() {
        Spacer(
            modifier = GlanceModifier.height(8.dp)
        )
    }

    @Preview(widthDp = 276, heightDp = 180, showBackground = true)
    @Composable
    fun PreviewLargeWidget() {
        GlanceTheme {
            WidgetLargeSize(
                context = LocalContext.current,
                ethusd = "3049.02",
                timestamp = "12:00 PM",
                lowGasPrice = "0.062239705",
                averageGasPrice = "0.062239707",
                highGasPrice = "0.062239705"
            )
        }
    }

    @Preview(widthDp = 130, heightDp = 180, showBackground = true)
    @Composable
    fun PreviewMediumWidget() {
        GlanceTheme {
            WidgetMediumSize(
                context = LocalContext.current,
                ethusd = "3049.02",
                lowGasPrice = "0.062239705",
                averageGasPrice = "0.062239707",
                highGasPrice = "0.062239705"
            )
        }
    }

    @Preview(widthDp = 130, heightDp = 100, showBackground = true)
    @Composable
    fun PreviewSmallWidget() {
        GlanceTheme {
            WidgetSmallSize(
                context = LocalContext.current,
                ethusd = "3049.02",
                lowGasPrice = "0.062239705",
                averageGasPrice = "0.062239707",
                highGasPrice = "0.062239705"
            )
        }
    }
}