package com.example.hotingoadmin.model

import com.google.gson.annotations.SerializedName

data class AdminModel(
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password :String)