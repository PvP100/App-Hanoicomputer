package com.example.utt.hnccomputer.entity.model

class Order {
}

enum class OrderType(val type: Int) {
    CHECK(0),
    UNCHECK(1),
    CANCEL(2);

    companion object {
        fun fromIntToOrderType(value: Int) = values().first { it.type == value}
    }
}
