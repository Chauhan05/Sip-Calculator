package com.example.sipapp.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.math.pow
import kotlin.math.roundToInt

class SipViewModel : ViewModel() {
    private val _amountPerMonth = mutableStateOf("5000")
    val amountPerMonth: State<String> = _amountPerMonth
    private val _interest = mutableStateOf("12")
    val interest: State<String> = _interest
    private val _timePeriod = mutableStateOf("10")
    val timePeriod: State<String> = _timePeriod

    private val _amountSliderValue = mutableFloatStateOf(5000f)
    val amountSliderValue: State<Float> = _amountSliderValue
    private val _timeSliderValue = mutableFloatStateOf(10f)
    val timeSliderValue: State<Float> = _timeSliderValue
    private val _interestSliderValue = mutableFloatStateOf(12f)
    val interestSliderValue: State<Float> = _interestSliderValue

    fun updateAmountPerMonth(newAmount: String) {
        _amountPerMonth.value = newAmount
        _amountSliderValue.floatValue=newAmount.toFloatOrNull()?:0f
    }

    fun updateInterest(newInterest: String) {
        _interest.value = newInterest
        _interestSliderValue.floatValue=newInterest.toFloatOrNull()?:0f
    }

    fun updateTimePeriod(newTime: String) {
        _timePeriod.value = newTime
        _timeSliderValue.floatValue=newTime.toFloatOrNull()?:0f
    }

    fun updateAmountSliderValue(newValue: Float){
        _amountSliderValue.floatValue=newValue
        _amountPerMonth.value=newValue.toInt().toString()
    }
    fun updateTimeSliderValue(newValue: Float){
        _timeSliderValue.floatValue=newValue
        _timePeriod.value=newValue.toInt().toString()
    }
    fun updateInterestSliderValue(newValue: Float){
        _interestSliderValue.floatValue=newValue
        _interest.value=newValue.toInt().toString()
    }
    fun calculateInvestedAmount(): Int {
        val amountPerMonth = amountPerMonth.value.toFloatOrNull() ?: 0f
        val timePeriod = timePeriod.value.toFloatOrNull() ?: 0f
        val investedAmount = amountPerMonth * timePeriod * 12
        return investedAmount.roundToInt()
    }

    fun calculateTotalAmount(): Int {
//    if(viewModel.interest.value=="" || viewModel.timePeriod.value=="") return 0
        val monthlyRate = (interest.value.toFloatOrNull() ?: 0f) / 12 / 100
        val months = (timePeriod.value.toFloatOrNull() ?: 0f) * 12
        val amountPerMonth = amountPerMonth.value.toFloatOrNull() ?: 0f

        val totalAmount = (amountPerMonth * ((1 + monthlyRate).pow(months) - 1) / monthlyRate * (1 + monthlyRate))
//    Log.d("Main","$totalAmount")
        if(totalAmount.isNaN()) return -1
        return totalAmount.roundToInt()
    }
}