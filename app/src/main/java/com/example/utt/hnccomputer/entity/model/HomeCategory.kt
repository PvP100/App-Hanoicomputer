package com.example.utt.hnccomputer.entity.model

import com.example.utt.hnccomputer.entity.model.Product

data class HomeCategory(
    val categoryTitle: String,
    val product: List<Product>
)