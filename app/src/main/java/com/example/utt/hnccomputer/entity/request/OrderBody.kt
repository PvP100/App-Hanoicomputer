package com.example.utt.hnccomputer.entity.request

data class OrderBody(
    val customerID: String,
    val details: List<OrderItem>
)

data class OrderItem(
    val price: Long,
    val productId: String,
    val quantity: Int
)