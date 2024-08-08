package com.example.weatherapp.retrofitapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val retrofit = Retrofit
    .Builder()
    .baseUrl("https://api.weatherapi.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val retrofitInstance = retrofit.create(ApiMethods :: class.java)