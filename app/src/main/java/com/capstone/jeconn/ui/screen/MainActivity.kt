package com.capstone.jeconn.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.capstone.jeconn.navigation.SetupNavGraph
import com.capstone.jeconn.ui.theme.JeConnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val modelML = "nsfwmodel.tflite"
//        val options = TfLiteInitializationOptions.builder()
//            .setEnableGpuDelegateSupport(true)
//            .build()
//
//        TfLiteVision.initialize(this, options).addOnSuccessListener {
//            objectDetectorListener.onInitialized()
//        }.addOnFailureListener {
//            // Called if the GPU Delegate is not supported on the device
//            TfLiteVision.initialize(this).addOnSuccessListener {
//                objectDetectorListener.onInitialized()
//            }.addOnFailureListener{
//                objectDetectorListener.onError("TfLiteVision failed to initialize: "
//                        + it.message)
//            }
//        }
//
//
//        val optionsBuilder =
//            ObjectDetector.ObjectDetectorOptions.builder()
//                .setScoreThreshold(threshold)
//                .setMaxResults(maxResults)

        setContent {
            JeConnTheme {
                val navHostController: NavHostController = rememberNavController()
                SetupNavGraph(navHostController = navHostController)
            }
        }
    }
}