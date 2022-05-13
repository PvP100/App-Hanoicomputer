package com.example.utt.hnccomputer.entity.request

data class RegisterRequest(
    val address: String,
    val birthday: String,
    val email: String,
    val fullName: String,
    val gender: Int,
    val phone: String
)