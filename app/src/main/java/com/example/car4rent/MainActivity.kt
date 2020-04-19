package com.example.car4rent

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //testing tan
        //Change ma
        Toast.makeText(applicationContext, "firebase connection success", Toast.LENGTH_LONG).show()
    }
}
