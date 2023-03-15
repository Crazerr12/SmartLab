package com.example.smartlab.presentation.models

import com.google.gson.annotations.SerializedName

data class TokenModel(
    @SerializedName("token")
    val token: String
)
