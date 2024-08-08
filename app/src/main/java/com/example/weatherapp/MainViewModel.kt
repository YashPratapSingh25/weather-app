package com.example.weatherapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.retrofitapi.WeatherResponse
import com.example.weatherapp.retrofitapi.retrofitInstance
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    data class LocationState(
        val location : LocationData? = null,
        val loading : Boolean = true
    )

    private val _locationState = mutableStateOf(LocationState())
    val locationState : State<LocationState> = _locationState

    fun updateLocation(newLocation : LocationData){
        _locationState.value = _locationState.value.copy(
            location = newLocation,
            loading = false
        )
    }

    data class WeatherResponseState(
        val weatherResponse: WeatherResponse? = null,
        val loading : Boolean = true,
        val error : String? = null
    )



    private val _weatherResponseState = mutableStateOf(WeatherResponseState())
    val weatherResponseState : State<WeatherResponseState> = _weatherResponseState
    fun getWeather(address : String){
        viewModelScope.launch {
            try {
                val response = retrofitInstance.getWeather(BuildConfig.API_KEY, address)
                _weatherResponseState.value = _weatherResponseState.value.copy(
                    weatherResponse = response,
                    loading = false,
                    error = null
                )
            } catch (e: Exception) {
                _weatherResponseState.value = _weatherResponseState.value.copy(
                    loading = false,
                    error = "Error occurred while fetching weather details: ${e.message} ${_weatherResponseState.value.weatherResponse}"
                )
            }
        }
    }
}