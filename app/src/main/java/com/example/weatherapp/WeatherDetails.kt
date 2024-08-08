package com.example.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.retrofitapi.WeatherResponse
import org.jetbrains.annotations.Async

@Composable
fun WeatherDetails(weatherResponseState : MainViewModel.WeatherResponseState) {

    if(weatherResponseState.loading){
        Row (
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Text(text = "Fetching Weather Details...")
        }
    }else if(weatherResponseState.error != null){
        Text(text = "Error: ${weatherResponseState.error}")
    }else{
        val weatherResponse = weatherResponseState.weatherResponse
        val weatherLocation = weatherResponse?.location
        val currentWeather = weatherResponse?.current
        val weatherCondition = currentWeather?.condition
        
        Column {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(35.dp))
                Text(text = "${weatherLocation?.name}, ", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
                Text(text = "${weatherLocation?.region} ", fontSize = 17.sp)
            }

            Spacer(modifier = Modifier.height(40.dp))

            AsyncImage(
                model = "https:${weatherCondition?.icon}".replace("64*64", "128*128"),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "${weatherCondition?.text}",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 20.sp, fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "${currentWeather?.temp_c} ° C",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Medium,
                fontSize = 40.sp
            )
        }

        Card {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column (
                    modifier = Modifier.padding(20.dp).weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "${currentWeather?.humidity}%", fontWeight = FontWeight.Medium, fontSize = 25.sp)
                    Text(text = "Humidity")
                }
                Column (
                    modifier = Modifier.padding(20.dp).weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "${currentWeather?.dewpoint_c}", fontWeight = FontWeight.Medium, fontSize = 25.sp)
                    Text(text = "Dew Point")
                }
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column (
                    modifier = Modifier.padding(20.dp).weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "${currentWeather?.pressure_mb} mb", fontWeight = FontWeight.Medium, fontSize = 25.sp)
                    Text(text = "Pressure")
                }
                Column (
                    modifier = Modifier.padding(20.dp).weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "${currentWeather?.wind_kph} km/h", fontWeight = FontWeight.Medium, fontSize = 25.sp)
                    Text(text = "Wind Speed")
                }
            }
        }
    }
}