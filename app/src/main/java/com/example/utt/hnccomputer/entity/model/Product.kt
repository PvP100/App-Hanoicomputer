package com.example.utt.hnccomputer.entity.model

data class Product(
    val id: String,
    val name: String,
    val price: Long,
    val category: String,
    val logoUrl: String,
    val numberSave: Int,
    val quantity: Int,
    val isSale: Int,
    val salePercent: Int,
    val salePrice: Int,
    val warranty: String
)