package com.example.utt.hnccomputer.entity.model

data class Customer(
    val id: String,
    val fullName: String,
    val phone: String,
    val address: String,
    val avatarUrl: String,
    val gender: Gender,
    val birthday: String,
    val email: String,
    val orderStaticView: OrderView
)

data class OrderView(
    val uncheck: Int,
    val cancel: Int,
    val success: Int
)