package com.example.car4rent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ProductAdapter(val mCtx: Context,val layoutResId: Int,val productList: List<Car>): ArrayAdapter<Car>(mCtx,layoutResId,productList)  {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val textViewName1 = view.findViewById<TextView>(R.id.textViewName1)

        val product = productList[position]

        textViewName.text = product.carModel
        textViewName1.text = product.transmition
       // textViewName.text = product.capacity

        //return data and show
        return view
    }

}


