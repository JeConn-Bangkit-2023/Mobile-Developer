package com.capstone.jeconn.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter

fun Context.drawableToBitmap(drawableRes: Int): Bitmap {
    return BitmapFactory.decodeResource(resources, drawableRes)
}

@Composable
fun bitmapToPainter(bitmap: Bitmap): Painter {
    return BitmapPainter(bitmap.asImageBitmap())
}