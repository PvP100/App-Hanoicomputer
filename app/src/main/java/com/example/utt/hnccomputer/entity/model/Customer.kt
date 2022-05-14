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

enum class UpdateType(val type: Int) {
    ADDRESS(0),
    BIRTHDAY(1),
    EMAIL(2),
    FULLNAME(3),
    GENDER(4),
    PHONE(5);

    companion object {
        fun fromIntToEditType(type: Int) = values().first { it.type == type }
    }
}