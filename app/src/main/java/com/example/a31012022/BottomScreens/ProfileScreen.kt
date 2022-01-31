package com.example.a31012022.BottomScreens

import android.annotation.SuppressLint
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.example.a31012022.R
import java.io.File
import java.util.*

@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun ProfileScreen(bottomNav:NavHostController) {
    val context = LocalContext.current
    val zatichka = context.resources.getDrawable(R.drawable.home_black,context.theme).toBitmap().asImageBitmap()
    var bitmap by remember {
        mutableStateOf(zatichka)
    }
    var uri: Uri? by remember {
        mutableStateOf(null)
    }
    var image: Uri? by remember {
        mutableStateOf(null)
    }

    val getPhoto = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
        it?.let {
            image = it
        }
    }
    val takePhoto  = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()){
        if (it)
            image = uri
    }

    image?.let {
       val picture = MediaStore.Images.Media.getBitmap(context.contentResolver,it)
        bitmap = picture.asImageBitmap()
    }
    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.align(Alignment.Center)) {
            Image(bitmap = bitmap, contentDescription = null, modifier = Modifier.size(200.dp))
            Button(onClick = { getPhoto.launch("image/*") }) {
                Text(text = "getContent")
            }
            Button(onClick = {
                val file = File(context.filesDir,UUID.randomUUID().toString() +".jpg")
                uri = FileProvider.getUriForFile(context,"com.example.a31012022.fileprovider",file)
                takePhoto.launch(uri)
            }) {
                Text(text = "takePhoto")
            }
        }
    }

}