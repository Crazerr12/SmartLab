package com.example.smartlab.presentation.models

data class CreateProfileModel(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val middlename: String,
    val bith: String,
    val pol: String,
    val image: String
)
