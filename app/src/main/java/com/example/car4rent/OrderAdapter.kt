package com.example.car4rent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class OrderAdapter(val mCtx: Context,val layoutResId: Int,val orderList: List<CustomerOrder>): ArrayAdapter<CustomerOrder>(mCtx,layoutResId,orderList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.textViewName)
        val textViewName1 = view.findViewById<TextView>(R.id.textViewName1)

        val book = orderList[position]

        textViewName.text = book.testdate
        textViewName1.text = book.testtime

        //return data and show
        return view
    }



}