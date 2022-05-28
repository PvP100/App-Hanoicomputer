package com.example.utt.hnccomputer.entity.model

import java.io.Serializable


data class Category(
    val id: Int,
    val title: String,
    val quantity: Int,
    val imgUrl: String,
    val categoryType: String,
    var isSelected: Boolean = false
) : Serializable