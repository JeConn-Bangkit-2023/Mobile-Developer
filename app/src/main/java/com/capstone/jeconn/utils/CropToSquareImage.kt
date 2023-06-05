package com.capstone.jeconn.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale

@Composable
fun CropToSquareImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val painter = // Set the desired size to a square
        rememberAsyncImagePainter(
            ImageRequest.Builder // Crop the image to fit the square
                (LocalContext.current).data(data = imageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    size(500, 500) // Set the desired size to a square
                    scale(Scale.FILL) // Crop the image to fit the square
                }).build()
        )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}