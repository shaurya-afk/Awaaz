package com.project.whistleblower.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance{
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val api: ComplaintApiService by lazy {
        Retrofit.Builder()
            .baseUrl("http://awaaz-backend-alb-820503857.eu-north-1.elb.amazonaws.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ComplaintApiService::class.java)
    }
}