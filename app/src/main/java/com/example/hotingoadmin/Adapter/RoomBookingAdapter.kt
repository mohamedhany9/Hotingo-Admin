package com.example.hotingoadmin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hotingoadmin.R
import com.example.hotingoadmin.RoomBookingsActivity
import com.example.hotingoadmin.model.RoomOrderResponce

class RoomBookingAdapter(val context: RoomBookingsActivity, var listner: OnClickListner) :
    RecyclerView.Adapter<RoomBookingAdapter.MyViewHolder>() {

    var roombooking: List<RoomOrderResponce> = listOf()


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {

        val view = LayoutInflater.from(p0.context).inflate(R.layout.room_booking_item, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return roombooking.size
    }


    interface OnClickListner {
        fun onServiceClick(roomid: String, serviceid: String)
    }

    fun setroombooking(roombooking: List<RoomOrderResponce>) {
        this.roombooking = roombooking;
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        with(holder.itemView) {
            holder.customername.text = roombooking.get(p1).user?.name
            holder.roomnumber.text = roombooking.get(p1).room?.number.toString()

            setOnClickListener {
                listner.onServiceClick(roombooking.get(p1).room?.id!!, roombooking.get(p1).id!!)

            }
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val customername: TextView = itemView.findViewById(R.id.customer_name_TV)
        val roomnumber: TextView = itemView.findViewById(R.id.room_number_TV)
    }
}