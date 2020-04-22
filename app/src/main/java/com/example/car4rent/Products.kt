package com.example.car4rent

class Products {
     var carId: String? = null
    private var carModel: String? = null
    private var transmition: String? = null
    private var price: String? = null
    var image: String? = null
    private var capacity: String? = null

    constructor(carId: String?, carModel: String?, transmition: String?, price: String?, image: String?, capacity: String?) {
        this.carId = carId
        this.carModel = carModel
        this.transmition = transmition
        this.price = price
        this.image = image
        this.capacity = capacity
    }

    fun getcarModel(): String?{
        return carModel
    }

    fun setcarModel(carModel: String?) {
        this.carModel = carModel
    }

    fun gettransmition(): String? {
        return transmition
    }

    fun settransmition(transmition: String?) {
        this.transmition = transmition
    }

    fun getprice(): String? {
        return price
    }

    fun setprice(price: String?) {
        this.price = price
    }

    fun getcapacity(): String? {
        return capacity
    }

    fun setcapacity(capacity: String?) {
        this.capacity = capacity
    }

    constructor() {}

}