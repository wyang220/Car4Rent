package com.example.car4rent

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    var _carName: TextView = itemView.findViewById<View>(R.id.car_Name) as TextView
    var _transmition: TextView = itemView.findViewById<View>(R.id.transmitions) as TextView
    var _capacity: TextView = itemView.findViewById<View>(R.id.capacitys) as TextView
    var _price: TextView = itemView.findViewById<View>(R.id.prices) as TextView
    var _imageView: ImageView = itemView.findViewById<View>(R.id.carImage) as ImageView


    var listner: ItemClickListner? = null

    fun setItemClickListner(listner: ItemClickListner?) {
        this.listner = listner
    }

    override fun onClick(view: View) {
        listner!!.onClick(view, adapterPosition, false)
    }

}