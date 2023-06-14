package com.capstone.jeconn.listener

import android.content.Context
import android.util.Log
import com.capstone.jeconn.R
import com.capstone.jeconn.retrofit.ApiConfig
import com.capstone.jeconn.utils.createImageAsFormReqBody
import com.capstone.jeconn.utils.resizeImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

interface ImageProcessingListener {
    fun onImageSafe()
    fun onImageUnsafe(errorMessage: String)
    fun onBadRequest(errorMessage: String)
    fun onServerError(errorMessage: String)
}

class ImageProcessor(private val context: Context, private val image: File, private val listener: ImageProcessingListener) {
    private val classificationImageApiService = ApiConfig.classificationImageApiService

    private fun processImage() {

        val nudityBodyPart = createImageAsFormReqBody(resizeImage(image),"image")
        val nudityResult = classificationImageApiService.imageDetection(nudityBodyPart)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val nudityResponse = nudityResult.execute()

                when (nudityResponse.code()) {
                    200 -> {
                        val resultMessage = nudityResponse.body()?.result
                        if (resultMessage != null && resultMessage.contains("safe", true)) {
                            listener.onImageSafe()
                        } else {
                            listener.onImageUnsafe(context.getString(R.string.image_classification_true))
                        }
                    }

                    400 -> {
                        listener.onBadRequest(context.getString(R.string.image_type_wrong))
                    }

                    else -> {
                        listener.onServerError(context.getString(R.string.server_fail))
                    }
                }
            } catch (e: IOException) {
                Log.e("exception", e.message.toString())
                listener.onServerError(context.getString(R.string.server_fail))
            }
        }
    }

    init {
        processImage()
    }
}
