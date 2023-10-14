package com.mikkipastel.gastracker.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
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

class GasTrackerWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {

        // In this method, load data needed to render the AppWidget.
        // Use `withContext` to switch to another thread for long running
        // operations.

        provideContent {
            GlanceTheme {
                setGasTrackerWidget()
            }
        }
    }


    @Preview (showBackground = true)
    @Composable
    private fun setGasTrackerWidget() {
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
                    text = "$1547.35",
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .fillMaxHeight()
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
                    Text(
                        text = "🐢",
                        style = TextStyle(
                            fontSize = 24.sp
                        )
                    )
                    Text(
                        text = "Low",
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "6 gwei",
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "$ 0.12",
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 16.sp
                        )
                    )
                }
                Spacer(
                    modifier = GlanceModifier.width(8.dp)
                )
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsPrimary)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🚶",
                        style = TextStyle(
                            fontSize = 24.sp
                        )
                    )
                    Text(
                        text = "Average",
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "6 gwei",
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "$ 0.12",
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 16.sp
                        )
                    )
                }
                Spacer(
                    modifier = GlanceModifier.width(8.dp)
                )
                Column(
                    modifier = GlanceModifier
                        .background(R.color.bsDanger)
                        .padding(8.dp)
                        .cornerRadius(16.dp)
                        .defaultWeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "⚡️",
                        style = TextStyle(
                            fontSize = 24.sp
                        )
                    )
                    Text(
                        text = "High",
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "6 gwei",
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "$ 0.12",
                        style = TextStyle(
                            color = ColorProvider(Color.White),
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }
    }
}