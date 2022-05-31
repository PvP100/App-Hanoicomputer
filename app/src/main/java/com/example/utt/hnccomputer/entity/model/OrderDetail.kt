package com.example.utt.hnccomputer.entity.model

data class OrderDetail(
    val customerName: String,
    val phone: String,
    val address: String,
    val totalPrice: Long,
    val order: Order,
    val listProduct: List<ProductOrder>,
)