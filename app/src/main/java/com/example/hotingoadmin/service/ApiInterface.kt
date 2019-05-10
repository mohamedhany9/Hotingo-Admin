package com.example.hotingoadmin.service

import com.example.hotingoadmin.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("login")
    fun getAdminData(@Body admin: AdminModel): Call<AdminResponce>


    @GET("room/jfjhjhm/admin")
    fun getRoomOrderResponce(
        @Header("Authorization") token
        : String
    ): Call<ArrayList<RoomOrderResponce>>


    @GET("room/{roomid}/admin/{serviceid}")
    fun getOneRoomResponce(
        @Header("Authorization") token: String,
        @Path("roomid") roomid: String,
        @Path("serviceid") serviceid: String
    ): Call<RoomOrderResponce>

    @PUT("room/{roomid}/admin/{serviceid}/accept")
    fun getAcceptRoomResponce(
        @Header("Authorization") token: String,
        @Path("roomid") roomid: String,
        @Path("serviceid") serviceid: String
    ): Call<RoomOrderResponce>


    @PUT("room/{roomid}/admin/{serviceid}/cancle")
    fun getCancleRoomResponce(
        @Header("Authorization") token: String,
        @Path("roomid") roomid: String,
        @Path("serviceid") serviceid: String
    ): Call<RoomOrderResponce>


    @GET("service/jfjhjhm/admin")
    fun getServiceOrderResponce(
        @Header("Authorization") token
        : String
    ): Call<ArrayList<ServiceOrderResponce>>


    @GET("service/{serviceid}/admin/{adminid}")
    fun getOneServiceRoomResponce(
        @Header("Authorization") token: String,
        @Path("serviceid") serviceid: String,
        @Path("adminid") adminid: String
    ): Call<ServiceOrderResponce>

    @PUT("service/{serviceid}/admin/{adminid}/accept")
    fun getAcceptServiceResponce(
        @Header("Authorization") token: String,
        @Path("serviceid") serviceid: String,
        @Path("adminid") adminid: String
    ): Call<ServiceOrderResponce>


    @PUT("service/{serviceid}/admin/{adminid}/cancle")
    fun getCancleServiceResponce(
        @Header("Authorization") token: String,
        @Path("serviceid") serviceid: String,
        @Path("adminid") adminid: String
    ): Call<ServiceOrderResponce>


    @Multipart
    @POST("room")
    fun getNewRoom(
        @Header("Authorization") token: String,
        @Part imgs:List< MultipartBody.Part>,
        @Part("number") number: Int,
        @Part("price") price: Int,
        @Part("desc") desc: RequestBody
    ): Call<PostNewRoom>
}