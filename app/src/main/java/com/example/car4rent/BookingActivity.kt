package com.example.car4rent

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.booking.*
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity: AppCompatActivity() {

    var Product: TextView? = null
    private var car_Name: TextView? = null
    private  var transmitions:TextView? = null
    private  var carImage : ImageView? = null
    private  var capacitys:TextView? = null
    private  var prices:TextView? = null
    private var cardID: String? = null
    var ref: DatabaseReference? = null
    private var loadingBar: ProgressDialog? = null
    private var bookingRandomKey: String? = null
    lateinit var BookRef: DatabaseReference
    private var confirm_button: Button? = null

    //for time and date
    var formate = SimpleDateFormat("dd MMM, YYYY", Locale.US)
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking)
        loadingBar = ProgressDialog(this)

        cardID = intent.extras!!["carId"].toString()

        ref = FirebaseDatabase.getInstance().reference.child("Car").child(cardID!!)
        confirm_button = findViewById<View>(R.id.confirm_button) as Button
        Product = findViewById<View>(R.id.Products) as TextView
        Product!!.text = cardID

        car_Name = findViewById<View>(R.id.car_Name) as TextView
        transmitions = findViewById<View>(R.id._transmition) as TextView
        capacitys = findViewById<View>(R.id._capacity) as TextView
        prices = findViewById<View>(R.id._prices) as TextView
        carImage = findViewById<View>(R.id.car_Image) as ImageView
       // getProductDetails(cardID!!)
        getCarDetail(cardID)

       // select time and date
        btn_show.setOnClickListener {
            val now = Calendar.getInstance()
            val dateTv = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR,year)
                selectedDate.set(Calendar.MONTH,month)
                selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                 dateTv.text = formate.format(selectedDate.time)
            },
                    now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            dateTv.show()
            val timeTv = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                timeTv.text = timeFormat.format(selectedTime.time)
            },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
            timeTv.show()
        }


        confirm_button!!.setOnClickListener { SaveProductIntoDatabase() }
    }
    //get data
    private fun getCarDetail(cardID: String?) {
        val ref = FirebaseDatabase.getInstance().reference.child("Car")
        ref.child(cardID!!).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val product :  Products = dataSnapshot.getValue<Products>(com.example.car4rent.Products::class.java)!!

                    car_Name!!.setText(product.getcarModel())
                    transmitions!!.setText(product.gettransmition())
                    prices!!.setText(product.getprice())
                    capacitys!!.setText(product.getcapacity())
                    Picasso.get().load(product.image).into(carImage)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //Toast.makeText()
            }
        })
    }

    /*private fun ValidateProductData() {
        if (dateTv == null) {
            Toast.makeText(this, "Please Select date", Toast.LENGTH_SHORT).show()
        }
        else if (timeTv) {
            Toast.makeText(this, "Please Select date", Toast.LENGTH_SHORT).show()
        } else {
            SaveProductIntoDatabase()
        }
    }*/

    private fun SaveProductIntoDatabase() {
        val ref = FirebaseDatabase.getInstance().getReference("Book")
        val bookId = ref.push().key
        bookingRandomKey = bookId

        val bookMap = HashMap<String, Any?>()
        bookMap["BookingId"] = bookingRandomKey
        bookMap["BookingDate"] = dateTv.text.toString()
        bookMap["BookingTime"] = timeTv.text.toString()
        bookMap["price"] = prices!!.text.toString()
        bookMap["carId"] = car_Name!!.text.toString()

        BookRef.child(bookingRandomKey!!).updateChildren(bookMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@BookingActivity, "Book successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@BookingActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
//                    loadingBar!!.dismiss()
//                    val message = task.exception.toString()
//                    Toast.makeText(this, "Error $message", Toast.LENGTH_SHORT).show()
                }
            }
    }
}