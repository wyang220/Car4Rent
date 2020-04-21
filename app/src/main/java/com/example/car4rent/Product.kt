package com.example.car4rent

class Product {
    var carId: String? = null
    var carModel: String? = null
    var transmition: String? = null
    var price: String? = null
    var image: String? = null
    var capacity: String? = null

    constructor(carId: String?, carModel: String?, transmition: String?, price: String?, image: String?, capacity: String?) {
        this.carId = carId
        this.carModel = carModel
        this.transmition = transmition
        this.price = price
        this.image = image
        this.capacity = capacity
    }

    constructor() {}
}

