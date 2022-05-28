package com.example.utt.hnccomputer.entity.model

import com.google.gson.annotations.SerializedName

data class Order(
    val id: Int,
    val isCheck: OrderStatus,
    val createdDate: Long,
    val updateDate: Long,
    val totalProduct: Int
)

enum class OrderStatus(val type: Int, status: String) {

    @SerializedName("1")
    CHECK(1, "Đã xử lý"),

    @SerializedName("0")
    UNCHECK(0, "Chưa xử lý"),

    @SerializedName("-1")
    CANCEL(-1, "Đã hủy");

    companion object {
        fun fromIntToOrderType(value: Int) = values().first { it.type == value}
    }
}
