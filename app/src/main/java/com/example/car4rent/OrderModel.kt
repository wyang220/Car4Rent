package com.example.car4rent

import android.util.Log
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

object OrderModel : Observable() {

    private var mValueListner: ValueEventListener? = null
    private val orderList : ArrayList<readData>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("order")
    }

    init {
        if (mValueListner != null) {
            getDatabaseRef()?.removeEventListener(mValueListner!!)
        }
        mValueListner = null
        Log.i("orderModel","data init line 24")

        mValueListner = object : ValueEventListener{
            override fun onCancelled(dataSnapshot: DatabaseError) {
                try {
                    Log.i("orderModel","data updated line 28")
                    val data:ArrayList<readData> = ArrayList()
//                    if(dataSnapshot != null){
//                        for(snapshot: DataSnapshot in dataSnapshot.children)
//                    }
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }

            override fun onDataChange(p0: DataSnapshot) {
            }

        }

    }

}