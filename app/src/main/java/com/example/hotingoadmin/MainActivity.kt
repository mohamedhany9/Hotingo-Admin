package com.example.hotingoadmin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_room_bookings.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        roomBookingsTV.setOnClickListener {
            var intent = Intent(this@MainActivity, RoomBookingsActivity::class.java)
            startActivity(intent)
        }
        serviceRequestsTV.setOnClickListener {
            var intent = Intent(this@MainActivity, ServicesRequestsActivity::class.java)
            startActivity(intent)
        }
        HotelRoomsTV.setOnClickListener {
            val intent = Intent(this@MainActivity, HotelsRoomActivity::class.java)
            startActivity(intent)
        }
    }
}
