package com.example.weatherapp.retrofitapi

data class WeatherResponse(
    val current: Current,
    val location: Location
)