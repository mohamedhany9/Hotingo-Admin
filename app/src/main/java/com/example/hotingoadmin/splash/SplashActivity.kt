package com.example.hotingoadmin.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.hotingoadmin.LoginActivity
import com.example.hotingoadmin.MainActivity
import com.example.hotingoadmin.R
import com.example.hotingoadmin.model.AdminResponce
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {

    var gson: Gson = Gson()
    lateinit var shared: SharedPreferences
    var retriveuser: String = ""
    lateinit var shareEditor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (progressBar != null ){
        Timer().schedule(object : TimerTask() {
            override fun run() {
                shared = getSharedPreferences("Hotingo Admin", Context.MODE_PRIVATE)
                shareEditor = shared.edit()

                retriveuser = shared.getString("ADMIN", "")
                var user = gson.fromJson(retriveuser, AdminResponce::class.java) ?: null

                if (user == null) {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 1200)

    }
}
}
