package com.user.teentalk.utils


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.io.File


fun createImagePickerIntent(isCamera: Boolean, photoUri: Uri): Intent {
    return if (isCamera) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        }
    } else {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }
}

@Composable
fun rememberImagePickerLauncher(onImagePicked: (Bitmap?) -> Unit): ActivityResultLauncher<Intent> {
    val context = LocalContext.current
    return androidx.activity.compose.rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri = result.data?.data
            val bitmap = if (imageUri != null) {
                context.loadBitmap(imageUri)
            } else {
                val photoFile = File(context.cacheDir, "temp_photo.jpg")
                if (photoFile.exists()) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.fromFile(photoFile))
                } else null
            }
            onImagePicked(bitmap)
        } else {
            onImagePicked(null)
        }
    }
}

fun Context.loadBitmap(uri: Uri): Bitmap? {
    return contentResolver.openInputStream(uri)?.use {
        BitmapFactory.decodeStream(it)
    }
}