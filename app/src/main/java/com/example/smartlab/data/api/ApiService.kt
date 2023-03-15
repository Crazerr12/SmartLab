package com.example.smartlab.data.api

import com.example.smartlab.presentation.models.*
import com.example.smartlab.presentation.profile.ProfileCardFragment
import com.google.gson.annotations.Until
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/sendCode")
    suspend fun sendCode(
        @Header("email") email: String
    ): Response<Unit>

    @POST("api/signin")
    suspend fun signIn(
        @Header("email") email: String,
        @Header("code") code: Int
    ): Response<TokenModel>

    @GET("api/news")
    suspend fun getNews(): Response<List<NewsModel>>

    @GET("api/catalog")
    suspend fun getProducts(): Response<List<ProductModel>>

    @POST("api/createProfile")
    suspend fun sendProfile(
        @Header("Authorization") token: String,
        @Body body: CreateProfileModel
    ) : Response<Unit>

    @POST("api/updateProfile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body body: UpdateProfileModel
    ) : Response<Unit>
}