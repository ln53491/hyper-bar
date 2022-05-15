package com.example.hyperbar.data

var bool = false

data class CategoryNew(
    val id: Long = 0,
    val name: String = ""
)

data class TodaysSelection(
    val id: Long = 0,
)


data class Product(
    val categoryId: Long = 0,
    val description: String = "",
    val id: Long = 0,
    val imageUrl: String = "",
    val name: String = "",
    val priceEur: Double = 0.0,
    val priceHrk: Long = 0,
    val servingSize: Double = 0.0
)



data class Order(
    val done: Long = 0,
    val orderId: Long = 0,
    val products: HashMap<String, Long> = hashMapOf<String, Long>(),
//    val products: ArrayList<Long> = arrayListOf(),
    val tableNumber: Long = 0,
    val totalPriceInEUR: Double = 0.0,
    val totalPriceInKN: Long = 0,
    val waiterId: Long = 0
)