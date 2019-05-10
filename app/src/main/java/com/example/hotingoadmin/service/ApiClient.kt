package com.example.hotingoadmin.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {

        val BASE_URL: String = "https://hotingoapp.herokuapp.com"
        var retrofit: Retrofit? = null

        fun getAdmin(): Retrofit {

            if (retrofit == null) {

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit!!
            }
            return retrofit!!
        }
    }
}