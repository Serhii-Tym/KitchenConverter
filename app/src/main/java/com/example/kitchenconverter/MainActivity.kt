package com.example.kitchenconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KitchenConverterScreen()
        }
    }
}

object Converters {
    fun fahrenheitToCelsius(fahrenheit: Double): Double {
        return (fahrenheit - 32) * 5 / 9
    }
    fun celsiusToFahrenheit(celsius: Double): Double {
        return celsius * 9 / 5 + 32
    }
}

@Composable
fun KitchenConverterScreen() {
    var userInput by remember {
        mutableStateOf("")
    }

    var conversionType by remember {
        mutableStateOf("Fahrenheit")
    }
    val number = userInput.toDoubleOrNull()
    val hasError = userInput.isNotEmpty() && number == null

    val result =
        if (number != null){
            if (conversionType == "Fahrenheit")
                Converters.fahrenheitToCelsius(number)
            else
                Converters.celsiusToFahrenheit(number)
        } else {
            null
        }

    val resultUnit =
        if (conversionType == "Fahrenheit")
            "°C"
        else
            "°F"


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Kitchen Converter",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Enter an amount in $conversionType",
            modifier = Modifier.padding(top = 24.dp)
        )
        OutlinedTextField(
            value = userInput,
            onValueChange = {
                userInput = it
            },
            label = {
                Text(text = "Amount in $conversionType")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            isError = hasError,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
            )
        Text(
            text = "Convert from",
            modifier = Modifier.padding(top = 24.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            RadioButton(
                selected = conversionType == "Fahrenheit",
                onClick = { conversionType = "Fahrenheit"}
            )
            Text(text = "Fahrenheit")
            
            RadioButton(
                selected = conversionType == "Celsius",
                onClick = { conversionType = "Celsius"}
            )
            Text(text = "Celsius")

        }
        Text(
            text = "Result",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp)
        )
        if(result != null) {
            Text(
                text = "%.1f %s".format(result, resultUnit),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        } else if (hasError) {
            Text(
                text = "Enter a valid temperature",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
        }
        if (hasError) {
            Text (
                text = "Please enter a number.",
                color = Color.Red
            )
        }
    }
}
