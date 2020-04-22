package com.example.car4rent

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var CarName: String? = null
    private var Transmition: String? = null
    private var Price: String? = null
    private var Capacity: String? = null
    private var productRandomKey: String? = null
    private var downloadImageUrl: String? = null
    private var AddNewProductButton: Button? = null
    private var InputImage: ImageView? = null
    private var InputName: EditText? = null
    private var InputTransmition: EditText? = null
    private var InputCapacity: EditText? = null
    private var InputPrice: EditText? = null
    private var ImagesRef: StorageReference? = null
    private var CarRef: DatabaseReference? = null
    private var ImageUri: Uri? = null
    private var loadingBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn.setOnClickListener {
            val intent = Intent(this@MainActivity,Test::class.java)
            // start your next activity
            startActivity(intent)
        }
    }


}

