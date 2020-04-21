package com.example.car4rent

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var ccarName: TextView = itemView.findViewById<View>(R.id.car_Name) as TextView
    var ctransmition: TextView = itemView.findViewById<View>(R.id.transmitions) as TextView
    var ccapacity: TextView = itemView.findViewById<View>(R.id.capacitys) as TextView
    var cprice: TextView = itemView.findViewById<View>(R.id.prices) as TextView
    var cimageView: ImageView = itemView.findViewById<View>(R.id.carImage) as ImageView


    var listner: ItemClickListner? = null

    fun setItemClickListner(listner: ItemClickListner?) {
        this.listner = listner
    }

     fun onClick(view: View) {
        listner!!.onClick(view, adapterPosition, false)
    }

    init {
        ccarName = itemView.findViewById<View>(R.id.car_Name) as TextView
        ctransmition = itemView.findViewById<View>(R.id.transmitions) as TextView
        ccapacity = itemView.findViewById<View>(R.id.capacitys) as TextView
        cprice = itemView.findViewById<View>(R.id.prices) as TextView
        cimageView = itemView.findViewById<View>(R.id.carImage) as ImageView
    }

}