package com.capstone.jeconn.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL_UPLOAD_iMAGE =
        "https://job-entertainment-connecting.et.r.appspot.com/"
    private const val BASE_URL_CLASSIFICATION_iMAGE =
        "https://model-ml-dot-job-entertainment-connecting.et.r.appspot.com/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private fun createOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private fun createRetrofit(baseUrl: String): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
    }


    val uploadImageApiService: ApiService =
        createRetrofit(BASE_URL_UPLOAD_iMAGE).create(ApiService::class.java)
    val classificationImageApiService: ApiService =
        createRetrofit(BASE_URL_CLASSIFICATION_iMAGE).create(ApiService::class.java)
}