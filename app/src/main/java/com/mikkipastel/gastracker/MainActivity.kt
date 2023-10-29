package com.mikkipastel.gastracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikkipastel.gastracker.mvvm.model.GasOracle
import com.mikkipastel.gastracker.mvvm.viewmodel.GasTrackerViewModel
import com.mikkipastel.gastracker.ui.theme.GasTrackerTheme
import com.mikkipastel.gastracker.ui.theme.bsDanger
import com.mikkipastel.gastracker.ui.theme.bsPrimary
import com.mikkipastel.gastracker.ui.theme.bsSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: GasTrackerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GasTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val etherPrice = viewModel.etherPrice.collectAsState()
                    GasTrackerView(
                        stringResource(id = R.string.header_ethereum),
                        etherPrice.value.ethusd.convertTo2Decimal(),
                        viewModel.gasOracle.collectAsState().value
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun GreetingPreview() {
    GasTrackerTheme {
        GasTrackerView(
            stringResource(id = R.string.header_ethereum),
            "1234.56",
            GasOracle(
                lowGasPrice = "7",
                averageGasPrice = "8",
                highGasPrice = "9"
            )
        )
    }
}

@Composable
fun GasTrackerView(headerText: String, ethusd: String, gasOracle: GasOracle) {
    Column (
        modifier = Modifier
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
    ) {
        HeaderText(headerText)
        TokenPriceView(ethusd)
        SpacerHeight8dp()
        GasPriceView(gasOracle)
    }
}

@Composable
fun HeaderText(headerText: String) {
    Text(
        text = headerText,
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(16.dp, 8.dp)
    )
}

@Composable
fun TokenPriceView(price: String?) {
    Row(
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth()
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_eth_diamond_purple),
            contentDescription = stringResource(id = R.string.logo_ethereum),
            modifier = Modifier
                .width(24.dp)
                .height(24.dp)
        )
        Text(
            text = stringResource(R.string.text_usd, price.toString()),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun GasPriceView(gasOracle: GasOracle) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Low
        Column (
            modifier = Modifier
                .background(
                    color = bsSuccess,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextEmoji(stringResource(id = R.string.emoji_gas_low))
            TextLabel(stringResource(id = R.string.text_gas_low))
            TextGwei(gasOracle.lowGasPrice)
        }
        SpacerWidth8dp()
        // Avg
        Column (
            modifier = Modifier
                .background(
                    color = bsPrimary,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextEmoji(stringResource(id = R.string.emoji_gas_avg))
            TextLabel(stringResource(id = R.string.text_gas_avg))
            TextGwei(gasOracle.averageGasPrice)
        }
        SpacerWidth8dp()
        // High
        Column (
            modifier = Modifier
                .background(
                    color = bsDanger,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextEmoji(stringResource(id = R.string.emoji_gas_high))
            TextLabel(stringResource(id = R.string.text_gas_high))
            TextGwei(gasOracle.highGasPrice)
        }
    }
}

@Composable
fun TextEmoji(emoji: String) {
    Text(
        text = emoji,
        fontSize = 24.sp
    )
}

@Composable
fun TextLabel(label: String) {
    Text(
        text = label,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TextGwei(gwei: String?) {
    Text(
        text = stringResource(id = R.string.text_gwei, gwei.toString()),
        textAlign = TextAlign.End,
        color = Color.White,
        fontSize = 18.sp
    )
}

@Composable
fun SpacerHeight8dp() {
    Spacer(modifier = Modifier.height(8.dp))
}
@Composable
fun SpacerWidth8dp() {
    Spacer(modifier = Modifier.width(8.dp))
}
