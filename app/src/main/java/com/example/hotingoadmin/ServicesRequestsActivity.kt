package com.example.hotingoadmin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.hotingoadmin.Adapter.ServicesRequestsAdapter
import com.example.hotingoadmin.model.AdminResponce
import com.example.hotingoadmin.model.ServiceOrderResponce
import com.example.hotingoadmin.service.ApiClient
import com.example.hotingoadmin.service.ApiInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_services_requests.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicesRequestsActivity : AppCompatActivity() {

    lateinit var recyclerAdapter: ServicesRequestsAdapter
    var gson: Gson = Gson()
    lateinit var shared: SharedPreferences
    var retriveuser: String = ""
    lateinit var shareEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services_requests)

        progressBar.visibility = View.VISIBLE

        recyclerAdapter = ServicesRequestsAdapter(this@ServicesRequestsActivity
            , object : ServicesRequestsAdapter.OnClickListner {
                override fun onServiceClick(serviceid: String, adminid: String) {
                    var intent = Intent(this@ServicesRequestsActivity, OneserviceOnOneServiceActivity::class.java)
                    var bundle = Bundle()
                    bundle.putString("SERVICE_ID", serviceid)
                    bundle.putString("ADMIN_ID", adminid)
                    intent.putExtra("SERVICE", bundle)
                    startActivity(intent)
                }
            })
        serviceRecyclerview.layoutManager = LinearLayoutManager(this)
        serviceRecyclerview.adapter = recyclerAdapter

        //Save Data in SharedPreferences
        shared = getSharedPreferences("Hotingo Admin", Context.MODE_PRIVATE)!!
        shareEditor = shared.edit()

        //Get Data From SharedPreferences
        retriveuser = shared.getString("ADMIN", "")
        var admin = gson.fromJson(retriveuser, AdminResponce::class.java)

        var token = "Bearer " + admin.token

        var apiInterface: ApiInterface = ApiClient.getAdmin().create(ApiInterface::class.java)

        var call: Call<ArrayList<ServiceOrderResponce>> = apiInterface.getServiceOrderResponce(token)
        call.enqueue(object : Callback<ArrayList<ServiceOrderResponce>> {
            override fun onFailure(call: Call<ArrayList<ServiceOrderResponce>>?, t: Throwable?) {

            }

            override fun onResponse(
                call: Call<ArrayList<ServiceOrderResponce>>?, response: Response<ArrayList<ServiceOrderResponce>>?
            ) {
                progressBar.visibility = View.GONE
                recyclerAdapter.setserviceItem(response?.body()!!)
            }
        })

    }
}
