package com.example.ducksapi

import retrofit2.Call
import retrofit2.http.GET

interface DucksApi {

    @GET("random")
    fun getDucks(): Call<Ducks>
}