package com.example.a31012022

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.libraries.maps.MapView
import java.lang.IllegalStateException

@SuppressLint("MissingPermission")
@Composable
fun MoreScreen() {
    val mapView = rememberMapView()
    val permission = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.RequestPermission() ){result->
        mapView.getMapAsync {
            it.isMyLocationEnabled = result
        }
    }

    val context = LocalContext.current

    LaunchedEffect(context) {
        permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
    Column {
        AndroidView(factory = {mapView},modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)){
            it.getMapAsync {
                it.uiSettings.isZoomControlsEnabled = true
            }
        }
        AndroidView(modifier = Modifier.fillMaxWidth(), factory = {
            AdView(context).apply {
                adUnitId = "ca-app-pub-3940256099942544/6300978111"
                adSize = AdSize.BANNER
                loadAd(AdRequest.Builder().build())
            }
        })
    }



}

@Composable
fun rememberMapView():MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map_frame
        }
    }
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val observer = mapLifeCycle(mapView = mapView)

    DisposableEffect(key1 = lifecycle,  ){
        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }

    return mapView
}

@Composable
fun mapLifeCycle(mapView: MapView):
        LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver{source, event ->
            when(event){
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                else -> throw IllegalStateException()
            }
        }
    }