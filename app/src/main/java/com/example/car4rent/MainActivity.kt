package com.example.car4rent

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

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
        ImagesRef = FirebaseStorage.getInstance().reference.child("Car Images")
        CarRef = FirebaseDatabase.getInstance().reference.child("Car")
        AddNewProductButton = findViewById<View>(R.id.btnUpload) as Button
        InputImage = findViewById<View>(R.id.imageView) as ImageView
        InputName = findViewById<View>(R.id.carName) as EditText
        InputTransmition = findViewById<View>(R.id.transmition) as EditText
        InputCapacity = findViewById<View>(R.id.capacity) as EditText
        InputPrice = findViewById<View>(R.id.price) as EditText
        loadingBar = ProgressDialog(this)
        InputImage!!.setOnClickListener { OpenGallery() }
        AddNewProductButton!!.setOnClickListener { ValidateProductData() }

        btn.setOnClickListener {
            val intent = Intent(this,test::class.java)
            // start your next activity
            startActivity(intent)
        }
    }

    private fun OpenGallery() {
        val galleryIntent = Intent()
        galleryIntent.action = Intent.ACTION_GET_CONTENT
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, GalleryPick)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GalleryPick && resultCode == Activity.RESULT_OK && data != null) {
            ImageUri = data.data
            InputImage!!.setImageURI(ImageUri)
        }
    }

    private fun ValidateProductData() {
        Transmition = InputTransmition!!.text.toString()
        Capacity = InputCapacity!!.text.toString()
        Price = InputPrice!!.text.toString()
        CarName = InputName!!.text.toString()
        if (ImageUri == null) {
            Toast.makeText(this, "Product image is mandatory", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(Transmition)) {
            Toast.makeText(this, "Please insert car transmition", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(Capacity)) {
            Toast.makeText(this, "Please insert car sit capacity", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Please insert car price(RM)", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(CarName)) {
            Toast.makeText(this, "Please insert car name", Toast.LENGTH_SHORT).show()
        } else {
            StoreCarInformation()
        }
    }

    private fun StoreCarInformation() {
        loadingBar!!.setTitle("Add New Car")
        loadingBar!!.setMessage("Please wait while we are adding car")
        loadingBar!!.setCanceledOnTouchOutside(false)
        loadingBar!!.show()
        var ref = FirebaseDatabase.getInstance().getReference("cars")
        val carId = ref.push().key
        productRandomKey = carId
        val filePath = ImagesRef!!.child(ImageUri!!.lastPathSegment + productRandomKey + ".jpg")
        val uploadTask = filePath.putFile(ImageUri!!)
        uploadTask.addOnFailureListener { e ->
            val message = e.toString()
            Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            loadingBar!!.dismiss()
        }.addOnSuccessListener {
            Toast.makeText(this@MainActivity, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }
                downloadImageUrl = filePath.downloadUrl.toString()
                filePath.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadImageUrl = task.result.toString()
                    Toast.makeText(this@MainActivity, "Image saved to database", Toast.LENGTH_SHORT).show()
                    SaveProductIntoDatabase()
                }
            }
        }
    }

    private fun SaveProductIntoDatabase() {
        val carMap = HashMap<String, Any?>()
        carMap["carId"] = productRandomKey
        carMap["carModel"] = CarName
        carMap["transmition"] = Transmition
        carMap["capacity"] = Capacity
        carMap["image"] = downloadImageUrl
        carMap["price"] = Price

        CarRef!!.child(productRandomKey!!).updateChildren(carMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@MainActivity, test::class.java)
                    startActivity(intent)
                    loadingBar!!.dismiss()
                    Toast.makeText(this@MainActivity, "Car added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    loadingBar!!.dismiss()
                    val message = task.exception.toString()
                    Toast.makeText(this@MainActivity, "Error $message", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val GalleryPick = 1
    }
}

