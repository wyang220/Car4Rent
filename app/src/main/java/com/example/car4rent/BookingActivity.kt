package com.example.car4rent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.booking.*
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity: AppCompatActivity() {

    lateinit var carImage: ImageView
    lateinit var car_Name: TextView
    lateinit  var transmitions:TextView
    lateinit  var capacitys:TextView
    lateinit  var prices:TextView
    private var carID: String? = null
    //for firedatabase and recyclerview
//    lateinit var ref: DatabaseReference
//    lateinit var recyclerView: RecyclerView
//    lateinit var layoutManager: RecyclerView.LayoutManager

//for time and date
    var formate = SimpleDateFormat("dd MMM, YYYY", Locale.US)
    var timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.booking)
//        ref = FirebaseDatabase.getInstance().reference.child("Car")
        carID = intent.getStringExtra("carId")

        carImage = findViewById<View>(R.id.carImage) as ImageView

        car_Name = findViewById<View>(R.id.car_Name) as TextView
        transmitions = findViewById<View>(R.id.transmitions) as TextView
        capacitys = findViewById<View>(R.id.capacitys) as TextView
        prices = findViewById<View>(R.id.prices) as TextView

        getCarDetail(carID)

       // select time and date
        btn_show.setOnClickListener {
            val now = Calendar.getInstance()
            val dateTv = DatePickerDialog(
                this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(Calendar.YEAR, year)
                    selectedDate.set(Calendar.MONTH, month)
                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val date = formate.format(selectedDate.time)
                    dateTv.setText(date)
                    Toast.makeText(this, "date : " + date, Toast.LENGTH_SHORT).show()
                },
                now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)
            )

            dateTv.show()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY,hourOfDay)
                selectedTime.set(Calendar.MINUTE,minute)
                val time = timeFormat.format(selectedTime.time)
                timeTv.setText(time)
            },
                now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
            timePicker.show()
            try {
                if(btn_show.text != "Pick Date and Time") {
                    val date = timeFormat.parse(btn_show.text.toString())
                    now.time = date
                }
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private fun getCarDetail(carID: String?) {

        val ref = FirebaseDatabase.getInstance().reference.child("Car")
        ref.child(carID!!).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val product = dataSnapshot.getValue(Product::class.java)
                    car_Name!!.text = product!!.carModel
                    transmitions!!.text = product.transmition
                    capacitys!!.text = product.capacity
                    car_Name!!.text = product.price
                    Picasso.get().load(product.image).into(carImage)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            //Toast.makeText()
            }
        })
    }
}