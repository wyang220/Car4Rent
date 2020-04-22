package com.example.car4rent

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

//import kotlinx.android.synthetic.main.test.view.*


class AdminCarDetail  : AppCompatActivity() {

    var Product: TextView? = null
    private var carModel: TextView? = null
    private var price: TextView? = null
    private var transmition: TextView? = null
    private var capacity: TextView? = null
    var ref: DatabaseReference? = null
    private var productID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.car_detail)

        productID = intent.extras!!["carId"].toString()
        //productID = intent.getStringExtra("carId")

        //display
        ref = FirebaseDatabase.getInstance().reference.child("Car").child(productID!!)
        Product = findViewById<View>(R.id.Products) as TextView
        Product!!.text = productID
        carModel = findViewById<View>(R.id.car_name_detail) as TextView
        transmition = findViewById<View>(R.id.car_transmition_detail) as TextView
        capacity = findViewById<View>(R.id.car_capacity_detail) as TextView
        price = findViewById<View>(R.id.car_price_detail) as TextView
        getProductDetails(productID)
    }


/*    private fun getProductDetails(productID: String) {
        val productsRef = FirebaseDatabase.getInstance().reference.child("Car")
        productsRef.child(productID).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    //val product :  List = dataSnapshot.getValue<List>(com.example.img20.List::class.java)!!
                    val products = dataSnapshot.getValue(List::class.java)

                    carModel!!.text = products!!.carModel
                    transmition!!.text = products.transmition
                    capacity!!.text = products.capacity
                    price!!.text = products.price

                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }*/

//    private fun getProductDetails(productID: String?) {
//        val ref = FirebaseDatabase.getInstance().reference.child("Car")
//        ref.child(productID!!).addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    val product :  Products = dataSnapshot.getValue<Products>(com.example.car4rent.Products::class.java)!!
//
//                    carModel!!.setText(product.carModel())
//                    transmition!!.setText(product.transmition())
//                    capacity!!.setText(product.capacity())
//                    price!!.setText(product.price())
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                //Toast.makeText()
//            }
//        })
//    }

    private fun getProductDetails(productID: String?) {
        val ref = FirebaseDatabase.getInstance().reference.child("Car")
        ref.child(productID!!).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val product :  Products = dataSnapshot.getValue<Products>(com.example.car4rent.Products::class.java)!!

                    carModel!!.setText(product.getcarModel())
                    transmition!!.setText(product.gettransmition())
                    capacity!!.setText(product.getprice())
                    price!!.setText(product.getcapacity())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //Toast.makeText()
            }
        })
    }

    fun btnDelete_Click(view: View?) {
        ref!!.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@AdminCarDetail, "Recored Deleted Successfully", Toast.LENGTH_LONG)
                finish()
            } else {
                Toast.makeText(this@AdminCarDetail, "Recored Deleted Unseccessfully", Toast.LENGTH_LONG)
            }
        }
    }
}