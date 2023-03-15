package com.example.smartlab.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit: ApiService = Retrofit.Builder()
        .baseUrl("https://medic.madskill.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}