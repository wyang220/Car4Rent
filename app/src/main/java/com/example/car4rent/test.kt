package com.example.car4rent

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class test: AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 1234
    private var filePath: Uri? = null
    lateinit var ref: DatabaseReference
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var shirt: ImageView
    lateinit var loadingBar: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_product)

        ref = FirebaseDatabase.getInstance().reference.child("Car")
        shirt = findViewById<View>(R.id.car) as ImageView
        loadingBar = ProgressDialog(this)
        recyclerView = findViewById(R.id.recycler_menu)
        recyclerView?.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView?.setLayoutManager(layoutManager)
    }

    override fun onStart() {
        super.onStart()
        val options = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(ref!!, Product::class.java)
            .build()
        val adapter: FirebaseRecyclerAdapter<Product, ProductViewHolder> = object : FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            override fun onBindViewHolder(holder: ProductViewHolder, position: Int, model:Product) {
                holder._carName.text = model.carModel
                holder._transmition.text = model.transmition
                holder._capacity.text = model.capacity
                holder._price.text = "Price = RM " + model.price + " .00 "
                Picasso.get().load(model.image).into(holder._imageView)

                holder.itemView.setOnClickListener {
                    val intent = Intent(this@test, BookingActivity::class.java)
                    intent.putExtra("carId",model.carId)
                    startActivity(intent)
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.product_item_list, parent, false)
                return ProductViewHolder(view)
            }
        }
        recyclerView!!.adapter = adapter
        adapter.startListening()
    }
}