package com.example.hotingoadmin.model

import com.google.gson.annotations.SerializedName

data class Service(
    @SerializedName("__v")
    val v: Int?,
    @SerializedName("_id")
    val id: String?,
    @SerializedName("avaliable")
    val avaliable: String?,
    @SerializedName("creationDate")
    val creationDate: String?,
    @SerializedName("desc")
    val desc: String?,
    @SerializedName("img")
    val img: String?,
    @SerializedName("name")
    val name: String?
)