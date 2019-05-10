package com.example.hotingoadmin.model

import com.google.gson.annotations.SerializedName

data class AdminResponce(
    @SerializedName("message")
    val message: String?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("user")
    val user: User?
)