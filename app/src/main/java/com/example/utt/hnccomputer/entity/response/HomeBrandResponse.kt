package com.example.utt.hnccomputer.entity.response

import com.example.utt.hnccomputer.entity.model.Brand

data class HomeBrandResponse(
    val categoryTitle: String,
    val brand: List<Brand>
)