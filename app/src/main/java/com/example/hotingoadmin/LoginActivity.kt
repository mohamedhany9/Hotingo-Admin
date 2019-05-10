package com.example.hotingoadmin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hotingoadmin.model.AdminModel
import com.example.hotingoadmin.model.AdminResponce
import com.example.hotingoadmin.service.ApiClient
import com.example.hotingoadmin.service.ApiInterface
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    var gson: Gson = Gson()
    lateinit var shared: SharedPreferences
    lateinit var shareEditor: SharedPreferences.Editor
    var json: String = ""
    var retriveuser: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        userButtonLogin.setOnClickListener {

            var userphone = userPhone.text.toString()
            var userpassword = userPassword.text.toString()

            if (userphone.isEmpty()) {
                userPhone.setError("Enter Phone Number")
            } else if (userpassword.isEmpty()) {
                userPassword.setError("Enter Password")
            } else {

                var apiInterface: ApiInterface = ApiClient.getAdmin().create(ApiInterface::class.java)
                var call: Call<AdminResponce> = apiInterface.getAdminData(AdminModel(userphone, userpassword))

                call.enqueue(object : Callback<AdminResponce> {
                    override fun onFailure(call: Call<AdminResponce>?, t: Throwable?) {
                        Toast.makeText(this@LoginActivity, "Fail", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<AdminResponce>?, response: Response<AdminResponce>?) {

                        Toast.makeText(this@LoginActivity, ""+response?.body()?.token, Toast.LENGTH_LONG).show()


                        var adminModel: AdminResponce? = response?.body()
                        shared = getSharedPreferences("Hotingo Admin", Context.MODE_PRIVATE)
                        shareEditor = shared.edit()
                        json = gson.toJson(adminModel)
                        shareEditor.putString("ADMIN",json)
                        shareEditor.commit()

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)

                    }
                })

            }

        }
    }
}
