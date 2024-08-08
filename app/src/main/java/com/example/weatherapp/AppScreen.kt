package com.example.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppScreen(
    viewModel : MainViewModel,
    locationUtils : LocationUtils
) {
    val location = viewModel.locationState.value
    val address = location.location?.let { locationUtils.reverseGeocoderLocation(it) }
    val weatherResponseState = viewModel.weatherResponseState.value
    val inputAddress = remember { mutableStateOf("") }
    val input = remember { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputAddress.value,
                onValueChange = { inputAddress.value = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Search") }
            )
            IconButton(onClick = {
                viewModel.getWeather(inputAddress.value)
                input.value = true
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search weather information"
                )
            }
        }
        locationUtils.getLocation(viewModel)
        if(location.loading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Fetching Location...")
            }
        }
        else{
            if (address != null && !input.value) {
                viewModel.getWeather(address)
            }
            WeatherDetails(weatherResponseState = weatherResponseState)
        }


    }
}