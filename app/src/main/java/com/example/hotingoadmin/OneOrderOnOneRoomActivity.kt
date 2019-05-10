package com.example.hotingoadmin

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hotingoadmin.model.AdminResponce
import com.example.hotingoadmin.model.RoomOrderResponce
import com.example.hotingoadmin.service.ApiClient
import com.example.hotingoadmin.service.ApiInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_one_order_on_one_room.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OneOrderOnOneRoomActivity : AppCompatActivity() {

    var gson: Gson = Gson()
    lateinit var shared: SharedPreferences
    var retriveuser: String = ""
    lateinit var shareEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_order_on_one_room)


        var roomId = intent.getBundleExtra("ORDER").getString("ROOM_ID", "")
        var serviceId = intent.getBundleExtra("ORDER").getString("ORDER_ID", "")


        //Save Data in SharedPreferences
        shared = getSharedPreferences("Hotingo Admin", Context.MODE_PRIVATE)!!
        shareEditor = shared.edit()

        //Get Data From SharedPreferences
        retriveuser = shared.getString("ADMIN", "")
        var admin = gson.fromJson(retriveuser, AdminResponce::class.java)

        var token = "Bearer " + admin.token

        var apiInterface: ApiInterface = ApiClient.getAdmin().create(ApiInterface::class.java)

        var call: Call<RoomOrderResponce> = apiInterface.getOneRoomResponce(token, roomId, serviceId)
        call.enqueue(object : Callback<RoomOrderResponce> {
            override fun onFailure(call: Call<RoomOrderResponce>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<RoomOrderResponce>?, response: Response<RoomOrderResponce>?) {

                roomnumberTv.text = "Room number : ${response?.body()?.room?.number.toString()}"
                roompriceTv.text = "Price for night : ${response?.body()?.room?.price.toString()}"
                customer_name_TV.text = "Customer Name : ${response?.body()?.user?.name.toString()}"
                phonenumTv.text = "Phone Num : ${response?.body()?.user?.phone.toString()}"
                durationTv.text = "Duration : ${response?.body()?.duration.toString()}"
                notesTv.text = "Duration : ${response?.body()?.notes.toString()}"
                AcceptTextTV.text = response?.body()?.status.toString()

                if (AcceptTextTV.text == "Rejected") {
                    Acceptbutton.visibility = View.VISIBLE
                    Rejectedbutton.visibility = View.GONE

                } else if (AcceptTextTV.text == "Accepted") {
                    Acceptbutton.visibility = View.GONE
                    Rejectedbutton.visibility = View.VISIBLE

                }
            }
        })

        Acceptbutton.setOnClickListener {
            var call1: Call<RoomOrderResponce> = apiInterface.getAcceptRoomResponce(token, roomId, serviceId)
            call1.enqueue(object : Callback<RoomOrderResponce> {
                override fun onFailure(call: Call<RoomOrderResponce>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<RoomOrderResponce>?, response: Response<RoomOrderResponce>?) {

                    Acceptbutton.visibility = View.GONE
                    Rejectedbutton.visibility = View.VISIBLE
                    AcceptTextTV.text = response?.body()?.status.toString()

                }
            })

        }

        Rejectedbutton.setOnClickListener {
            var call2 :Call<RoomOrderResponce> = apiInterface.getCancleRoomResponce(token,roomId,serviceId)
            call2.enqueue(object :Callback<RoomOrderResponce>{
                override fun onFailure(call: Call<RoomOrderResponce>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<RoomOrderResponce>?, response: Response<RoomOrderResponce>?) {

                    Acceptbutton.visibility = View.VISIBLE
                    Rejectedbutton.visibility = View.GONE
                    AcceptTextTV.text = response?.body()?.status.toString()
                }
            })
        }
    }
}
