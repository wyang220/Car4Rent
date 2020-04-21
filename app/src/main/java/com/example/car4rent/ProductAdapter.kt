package com.example.car4rent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ProductAdapter(val mCtx: Context,val layoutResId: Int,val productList: List<Product>): ArrayAdapter<Product>(mCtx,layoutResId,productList)  {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(layoutResId, null)

        val carName = view.findViewById<TextView>(R.id.carName)
        val carTransmition = view.findViewById<TextView>(R.id.transmition)
        val carCapacity = view.findViewById<TextView>(R.id.capacity)
        val carPrice = view.findViewById<TextView>(R.id.price)

        val product = productList[position]

        carName.text = product.carModel
        carTransmition.text = product.transmition
        carCapacity.text = product.capacity
        carPrice.text = product.price

        //return data and show
        return view
    }

}


