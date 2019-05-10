package com.example.hotingoadmin.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.hotingoadmin.R
import com.example.hotingoadmin.ServicesRequestsActivity
import com.example.hotingoadmin.model.ServiceOrderResponce

class ServicesRequestsAdapter(val context: ServicesRequestsActivity , var listner: OnClickListner) :
    RecyclerView.Adapter<ServicesRequestsAdapter.MyViewHolder>() {

    var serviceItem: List<ServiceOrderResponce> = listOf()


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {

        val view = LayoutInflater.from(p0.context).inflate(R.layout.services_requests_item, p0, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return serviceItem.size
    }


    interface OnClickListner {
        fun onServiceClick(serviceid: String , adminid :String)
    }

    fun setserviceItem(serviceItem: List<ServiceOrderResponce>) {
        this.serviceItem = serviceItem;
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        with(holder.itemView) {
            holder.requestName.text = serviceItem.get(p1).service?.name
            holder.customerService.text = serviceItem.get(p1).user?.name

            setOnClickListener {
                listner.onServiceClick(serviceItem.get(p1).service?.id!!, serviceItem.get(p1).id!!)

            }
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val requestName: TextView = itemView.findViewById(R.id.request_name_TV)
        val customerService: TextView = itemView.findViewById(R.id.customer_service_TV)
    }
}