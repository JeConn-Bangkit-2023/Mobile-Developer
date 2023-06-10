package com.capstone.jeconn.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun Context.drawableToBitmap(drawableRes: Int): Bitmap {
    return BitmapFactory.decodeResource(resources, drawableRes)
}

@Composable
fun bitmapToPainter(bitmap: Bitmap): Painter {
    return BitmapPainter(bitmap.asImageBitmap())
}

fun resizeBitmap(bitmap: Bitmap): Bitmap {
    val targetWidth = 224
    val targetHeight = 224
    return Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)
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