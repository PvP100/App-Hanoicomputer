package com.example.utt.hnccomputer.entity.request

data class OrderBody(
    val address: String,
    val customerID: String,
    val customerName: String,
    val phoneNumber: String,
    val details: List<OrderItem>
)

data class OrderItem(
    val price: Long,
    val productId: String,
    val quantity: Int
)