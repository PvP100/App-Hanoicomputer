package com.example.utt.hnccomputer.entity.model

class ProductOrder(
    val productId: String,
    val quantity: Int,
    val price: Long,
    val total: Long,
    val productUrl: String,
    val productName: String,
)