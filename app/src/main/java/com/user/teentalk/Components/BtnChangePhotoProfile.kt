package com.user.teentalk.Components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.user.teentalk.R
import com.user.teentalk.ui.theme.PoppinsFontFamily
import com.user.teentalk.utils.createImagePickerIntent
import com.user.teentalk.utils.rememberImagePickerLauncher
import java.io.File

@Composable
fun BtnChangePhotoProfile(onImagePicked: (Bitmap?) -> Unit) {
    val context = LocalContext.current
    val photoFile = remember { File(context.cacheDir, "temp_photo.jpg") }
    val photoUri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        photoFile
    )

    val imagePickerLauncher = rememberImagePickerLauncher(onImagePicked)

    Column {
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            colors =  ButtonDefaults.buttonColors(colorResource(id = R.color.dongker)),
            onClick = {
            val intent = createImagePickerIntent(isCamera = false, photoUri)
            imagePickerLauncher.launch(intent)
        }) {
            Text(
                "Ambil dari Galeri",
                fontFamily = PoppinsFontFamily
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            colors =  ButtonDefaults.buttonColors(colorResource(id = R.color.dongker)),
            onClick = {
            val intent = createImagePickerIntent(isCamera = true, photoUri)
            imagePickerLauncher.launch(intent)
        }) {
            Text(
                "Ambil dari Kamera",
                fontFamily = PoppinsFontFamily
            )
        }
    }
}



