package com.example.sipapp.screens

import PieChart
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sipapp.model.SipViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: SipViewModel,
    modifier: Modifier = Modifier
) {

    val formatter = remember {
//        Log.d("MainScreen", "MainScreen: ")
        NumberFormat.getInstance(Locale("en", "IN"))
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Spacer(modifier = Modifier.height(32.dp))
        Heading()
        Col(
            "Monthly investment",
            viewModel.amountPerMonth.value,
            Icons.Filled.AttachMoney,
            viewModel.amountSliderValue.value,
            valueRange = 500f..100000f,
            sliderValueOnChange = { viewModel.updateAmountSliderValue(it) },
            onValueChange = { viewModel.updateAmountPerMonth(it) }
        )
        Col(
            "Expected return",
            viewModel.interest.value,
            Icons.Filled.Percent,
            viewModel.interestSliderValue.value,
            valueRange = 1f..30f,
            sliderValueOnChange = { viewModel.updateInterestSliderValue(it) },
            onValueChange = { viewModel.updateInterest(it) }
        )
        Col(
            "Time Period (yrs)",
            viewModel.timePeriod.value,
            Icons.Filled.Schedule,
            viewModel.timeSliderValue.value,
            valueRange = 1f..50f,
            sliderValueOnChange = { viewModel.updateTimeSliderValue(it) },
            onValueChange = { viewModel.updateTimePeriod(it) }
        )
        val investedAmount = viewModel.calculateInvestedAmount()
        var totalAmount = viewModel.calculateTotalAmount()
        var interestAmount = totalAmount - investedAmount
        if (totalAmount <= 0) {
            interestAmount = 0
            totalAmount = investedAmount
        }
        TextCmp("Invested amount: ", formatter.format(investedAmount))
        TextCmp("Est. returns:", formatter.format(interestAmount))
        TextCmp("Total value:", formatter.format(totalAmount))
        PieChart(investedAmount.toFloat(), interestAmount.toFloat())
//        Col("Expected Return", viewModel)
//        Col("Time Period", viewModel)s
    }
}


@Composable
fun TextCmp(label: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 18.sp)
        Text(text = value, fontSize = 20.sp)
    }
}

@Composable
fun TextComponent(label: String, value: String, modifier: Modifier = Modifier) {
    Text(
        "$label : $value", fontSize = 18.sp, modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp), textAlign = TextAlign.Center
    )
}


@Composable
fun Col(
    label: String,
    value: String,
    iconImage: ImageVector,
    sliderValue: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    sliderValueOnChange: (Float) -> Unit,
    onValueChange: (String) -> Unit,
) {
    val focus = LocalFocusManager.current
    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label, modifier = Modifier.weight(1.5f), fontSize = 20.sp)
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                leadingIcon = {
                    Icon(iconImage, contentDescription = "")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focus.clearFocus()
                })
            )
        }

        Slider(
            value = sliderValue,
            onValueChange = sliderValueOnChange,
            valueRange = valueRange,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Heading(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(text = "Sip Calculator", color = Color.White)
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }
    )
//    Text(
//        text = "Sip Calculator",
//        fontSize = 24.sp,
//        modifier = Modifier.fillMaxWidth(),
//        textAlign = TextAlign.Center,
//        fontWeight = FontWeight.Bold
//    )
}

@Preview(showSystemUi = true)
@Composable
private fun MainScreenPreview() {
    MainScreen(navController = rememberNavController(), viewModel = viewModel())
}