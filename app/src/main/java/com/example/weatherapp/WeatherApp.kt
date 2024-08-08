package com.example.weatherapp

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WeatherApp() {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    val hasLocationPermission = remember{ mutableStateOf(locationUtils.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) }
    val viewModel : MainViewModel = viewModel()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            isGranted ->
            if(isGranted){
                hasLocationPermission.value = true
            }else{
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(context as MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                if(rationaleRequired)
                    Toast.makeText(context, "Location permission is required for this app to function.", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(context, "Grant location permission in app settings.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    if(hasLocationPermission.value)
        AppScreen(viewModel, locationUtils)
    else{
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Location permission is required for this app to function.")
            Button(onClick = { locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION) }) {
                Text(text = "Grant Permission.")
            }
        }
    }
}