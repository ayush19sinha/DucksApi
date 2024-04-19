package com.example.ducksapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://random-d.uk/api/"

    // Lazy initialization of Retrofit instance
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient {
    // Lazy initialization of DucksApi service using RetrofitClient
    val apiService: DucksApi by lazy {
        RetrofitClient.retrofit.create(DucksApi::class.java)
    }
}