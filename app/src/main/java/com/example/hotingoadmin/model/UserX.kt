package com.example.hotingoadmin.model

import com.google.gson.annotations.SerializedName

data class UserX(
    @SerializedName("__v")
    val v: Int?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("creationDate")
    val creationDate: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("img")
    val img: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("type")
    val type: String?
)