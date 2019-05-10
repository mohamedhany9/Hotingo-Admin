package com.example.hotingoadmin.model

import com.google.gson.annotations.SerializedName

data class Room(
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
    @SerializedName("imgs")
    val imgs: List<String?>?,
    @SerializedName("number")
    val number: String?,
    @SerializedName("price")
    val price: Int?
)