package com.example.weatherapp.retrofitapi

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMethods {
    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") apiKey : String,
        @Query("q") locationQuery : String
    ) : WeatherResponse
}