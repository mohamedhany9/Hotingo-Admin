package com.example.hotingoadmin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.hotingoadmin.Adapter.RoomBookingAdapter
import com.example.hotingoadmin.model.AdminResponce
import com.example.hotingoadmin.model.RoomOrderResponce
import com.example.hotingoadmin.service.ApiClient
import com.example.hotingoadmin.service.ApiInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_room_bookings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomBookingsActivity : AppCompatActivity() {

    lateinit var recyclerview: RecyclerView
    lateinit var recyclerAdapter: RoomBookingAdapter
    var gson: Gson = Gson()
    lateinit var shared: SharedPreferences
    var retriveuser: String = ""
    lateinit var shareEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_bookings)

        progressBar.visibility = View.VISIBLE


                recyclerview = findViewById(R.id.recyclerviewRoomBooking)
            recyclerAdapter = RoomBookingAdapter(this, object : RoomBookingAdapter.OnClickListner {
                override fun onServiceClick(roomid: String, serviceid: String) {

                    var intent = Intent(this@RoomBookingsActivity, OneOrderOnOneRoomActivity::class.java)
                    var bundle = Bundle()
                    bundle.putString("ROOM_ID", roomid)
                    bundle.putString("ORDER_ID", serviceid)
                    intent.putExtra("ORDER", bundle)
                    startActivity(intent)
                }

            })
            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = recyclerAdapter

            //Save Data in SharedPreferences
            shared = getSharedPreferences("Hotingo Admin", Context.MODE_PRIVATE)!!
            shareEditor = shared.edit()

            //Get Data From SharedPreferences
            retriveuser = shared.getString("ADMIN", "")
            var admin = gson.fromJson(retriveuser, AdminResponce::class.java)

            var token = "Bearer " + admin.token

            var apiInterface: ApiInterface = ApiClient.getAdmin().create(ApiInterface::class.java)

            var call: Call<ArrayList<RoomOrderResponce>> = apiInterface.getRoomOrderResponce(token)
            call.enqueue(object : Callback<ArrayList<RoomOrderResponce>> {

                override fun onFailure(call: Call<ArrayList<RoomOrderResponce>>?, t: Throwable?) {

                }

                override fun onResponse(
                    call: Call<ArrayList<RoomOrderResponce>>?, response: Response<ArrayList<RoomOrderResponce>>?
                ) {
                    progressBar.visibility = View.GONE
                    recyclerAdapter.setroombooking(response?.body()!!)
                }
            })
        }
    }
