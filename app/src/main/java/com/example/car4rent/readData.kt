package com.example.car4rent

import com.google.firebase.database.DataSnapshot


class readData(snapshot: DataSnapshot){

    lateinit var id : String
    lateinit var bdate : String
    lateinit var btime : String

    init {
        try{
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?:""
            bdate = data["bdate"] as String
            btime = data["btime"] as String
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}