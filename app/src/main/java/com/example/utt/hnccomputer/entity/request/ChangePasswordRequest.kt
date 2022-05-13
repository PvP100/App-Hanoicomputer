package com.example.utt.hnccomputer.entity.request

data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
)