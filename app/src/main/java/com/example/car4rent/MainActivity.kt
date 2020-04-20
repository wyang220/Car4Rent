package com.example.car4rent


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class MainActivity : AppCompatActivity(){

    lateinit var BookingTime : EditText
    lateinit var BookingDate : EditText
    lateinit var SaveButton : Button
    lateinit var listView : ListView

    lateinit var orderList : MutableList<CustomerOrder>
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        orderList = mutableListOf()
        ref = FirebaseDatabase.getInstance().getReference("books")

        BookingTime = findViewById(R.id.BookingTime)
        BookingDate = findViewById(R.id.BookingDate)
        SaveButton = findViewById(R.id.SaveButton)
        listView = findViewById(R.id.order_list)

        SaveButton.setOnClickListener{
           saveBooking()
       }

        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    orderList.clear()//declare multiple list
                    for (o in p0.children){
                        val order = o.getValue(CustomerOrder::class.java)
                        orderList.add(order!!)
                    }

                    val adapter = OrderAdapter(applicationContext, R.layout.cust_ordering, orderList)
                    listView.adapter = adapter
                }
            }

        })
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


        val bookId = ref.push().key

        val book = CustomerOrder(bookId.toString(),BookDate,BookTime)
        ref .child(BookDate).setValue(book).addOnCompleteListener{
            Toast.makeText(applicationContext, "Booking success, thanks", Toast.LENGTH_LONG).show()
        }
    }


}
