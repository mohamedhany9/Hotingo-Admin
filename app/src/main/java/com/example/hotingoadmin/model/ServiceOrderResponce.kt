package com.example.hotingoadmin.model

import com.google.gson.annotations.SerializedName

data class ServiceOrderResponce(
    @SerializedName("__v")
    val v: Int?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("creationDate")
    val creationDate: String?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("roomNo")
    val roomNo: Int?,
    @SerializedName("service")
    val service: Service?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("user")
    val user: UserX?
)