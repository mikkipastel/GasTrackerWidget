package com.mikkipastel.gastracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikkipastel.gastracker.mvvm.viewmodel.GasTrackerViewModel
import com.mikkipastel.gastracker.ui.theme.GasTrackerTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: GasTrackerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.apply {
            getGasOracle()
            getEtherLastPrice()
        }
        setContent {
            GasTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HeaderText("Ethereum")
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
fun GreetingPreview() {
    GasTrackerTheme {
        Column {
            HeaderText("Ethereum")
        }
    }
}

@Composable
fun HeaderText(headerText: String) {
    Text(
        text = headerText,
        color = Color.White,
        fontSize = 20.sp,
        modifier = Modifier.padding(16.dp, 8.dp)
    )
}
