package com.example.hotingoadmin

import android.content.Context
import android.content.SharedPreferences
import android.net.Network
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hotingoadmin.model.AdminResponce
import com.example.hotingoadmin.model.ServiceOrderResponce
import com.example.hotingoadmin.service.ApiClient
import com.example.hotingoadmin.service.ApiInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_oneservice_on_one_service.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OneserviceOnOneServiceActivity : AppCompatActivity() {

    var gson: Gson = Gson()
    lateinit var shared: SharedPreferences
    var retriveuser: String = ""
    lateinit var shareEditor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oneservice_on_one_service)


        var serviceID = intent.getBundleExtra("SERVICE").getString("SERVICE_ID", "")
        var adminID = intent.getBundleExtra("SERVICE").getString("ADMIN_ID", "")

        //Save Data in SharedPreferences
        shared = getSharedPreferences("Hotingo Admin", Context.MODE_PRIVATE)!!
        shareEditor = shared.edit()

        //Get Data From SharedPreferences
        retriveuser = shared.getString("ADMIN", "")
        var admin = gson.fromJson(retriveuser, AdminResponce::class.java)

        var token = "Bearer " + admin.token

        var apiInterface: ApiInterface = ApiClient.getAdmin().create(ApiInterface::class.java)

        var call :Call<ServiceOrderResponce> = apiInterface.getOneServiceRoomResponce(token,serviceID,adminID)
        call.enqueue(object :Callback<ServiceOrderResponce>{
            override fun onFailure(call: Call<ServiceOrderResponce>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<ServiceOrderResponce>?, response: Response<ServiceOrderResponce>?) {

                typeTv.text = "Type : ${response?.body()?.service?.name}"
                roomTv.text  ="Room : ${ response?.body()?.roomNo.toString()}"
                notesTv.text = "Notes : ${response?.body()?.note}"
                customer_name_TV.text = "Customer name : ${response?.body()?.user?.name}"
                phonenumTv.text = "Phone num : ${response?.body()?.user?.phone}"

                Accept2TextTV.text = response?.body()?.status

                if (Accept2TextTV.text == "Rejected") {
                    AcceptServicebutton.visibility = View.VISIBLE
                    RejectedServicebutton.visibility = View.GONE

                } else if (Accept2TextTV.text == "Accepted") {
                    AcceptServicebutton.visibility = View.GONE
                    RejectedServicebutton.visibility = View.VISIBLE

                }
            }
        })


        AcceptServicebutton.setOnClickListener {
            var call1: Call<ServiceOrderResponce> = apiInterface.getAcceptServiceResponce(token, serviceID, adminID)
            call1.enqueue(object : Callback<ServiceOrderResponce> {
                override fun onFailure(call: Call<ServiceOrderResponce>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<ServiceOrderResponce>?, response: Response<ServiceOrderResponce>?) {

                    AcceptServicebutton.visibility = View.GONE
                    RejectedServicebutton.visibility = View.VISIBLE
                    Accept2TextTV.text = response?.body()?.status.toString()

                }
            })

        }

        RejectedServicebutton.setOnClickListener {
            var call2 :Call<ServiceOrderResponce> = apiInterface.getCancleServiceResponce(token,serviceID,adminID)
            call2.enqueue(object :Callback<ServiceOrderResponce>{
                override fun onFailure(call: Call<ServiceOrderResponce>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<ServiceOrderResponce>?, response: Response<ServiceOrderResponce>?) {

                    AcceptServicebutton.visibility = View.VISIBLE
                    RejectedServicebutton.visibility = View.GONE
                    Accept2TextTV.text = response?.body()?.status.toString()
                }
            })
        }

    }
}
