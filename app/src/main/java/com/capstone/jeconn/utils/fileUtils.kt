package com.capstone.jeconn.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.core.graphics.scale
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

fun Context.drawableToBitmap(drawableRes: Int): Bitmap {
    return BitmapFactory.decodeResource(resources, drawableRes)
}

@Composable
fun bitmapToPainter(bitmap: Bitmap): Painter {
    return BitmapPainter(bitmap.asImageBitmap())
}

fun resizeImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    val targetWidth = 224
    val targetHeight = 224
    bitmap.scale(targetWidth, targetHeight, false)
    return file
}

private const val MAXIMAL_SIZE = 1000000 // max 1mb
fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun createImageAsFormReqBody(image: File, key: String): MultipartBody.Part {

    return MultipartBody.Part.createFormData(
        key,
        image.name,
        reduceFileImage(image).asRequestBody("image/jpeg".toMediaTypeOrNull())
    )
}

fun createTempFiles(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {

    val myFile = createTempFiles(context)
    val contentResolver: ContentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) {
        outputStream.write(buf, 0, len)
        Log.e("size", len.toString())
    }
    outputStream.close()
    inputStream.close()

    return myFile
}

val timeStamp: String = SimpleDateFormat(
    patternWithHours,
    Locale.US
).format(System.currentTimeMillis())