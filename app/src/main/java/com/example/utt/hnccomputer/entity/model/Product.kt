package com.example.utt.hnccomputer.entity.model

data class Product(
    val id: String,
    val name: String,
    val price: Long,
    val category: Category,
    val logoUrl: String,
    val description: String,
    val numberSave: Int,
    val quantity: Int,
    val isSale: Int,
    val salePercent: Int,
    val salePrice: Long,
    val warranty: String
)