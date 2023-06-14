package com.capstone.jeconn.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun OpenImageDialog(
    showDialogState: MutableState<Boolean>,
    imageUrl: String
) {
    if (showDialogState.value) {
        AlertDialog(
            onDismissRequest = { showDialogState.value = false },
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                GlideImage(
                    model = imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .clickable { showDialogState.value = false },
                ) {
                    it
                }
            }
        }
    }
}