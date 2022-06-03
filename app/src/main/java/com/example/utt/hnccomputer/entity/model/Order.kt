package com.example.utt.hnccomputer.entity.model

import com.google.gson.annotations.SerializedName

data class Order(
    val id: Int,
    var isCheck: OrderStatus,
    val createdDate: Long,
    var updateDate: Long,
    val totalProduct: Int
)

enum class OrderStatus(val type: Int, val status: String) {

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
