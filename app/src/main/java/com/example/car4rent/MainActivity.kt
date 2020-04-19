package com.example.car4rent

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {

    lateinit var BookingTime : EditText
    lateinit var BookingDate : EditText
    lateinit var SaveButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BookingTime = findViewById(R.id.BookingTime)
        BookingDate = findViewById(R.id.BookingDate)
        SaveButton = findViewById(R.id.SaveButton)

        SaveButton.setOnClickListener{
           saveBooking()
       }
    }

    private fun saveBooking()
    {
        val BookDate = BookingDate.text.toString().trim()
        val BookTime = BookingTime.text.toString().trim()

        if(BookDate.isEmpty())
        {
            BookingDate.error = "Select the Date"
            return
        }
        else if(BookTime.isEmpty())
        {
            BookingTime.error = "select the Time"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("books")
       // val bookId = ref.push().key
        val bookId = ref.push().key

        val book = CustomerOrder(bookId.toString(),BookDate,BookTime)
        ref .child(BookDate).setValue(book).addOnCompleteListener{
            Toast.makeText(applicationContext, "Booking success, thanks", Toast.LENGTH_LONG).show()
        }
    }
}
