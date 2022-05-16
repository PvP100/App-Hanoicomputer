package com.example.utt.hnccomputer.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "my_order")
data class MyOrderInformation(
    @ColumnInfo(name = "price")
    val price: Long,

    @ColumnInfo(name = "product_id")
    val productId: String,

    @ColumnInfo(name = "quantity")
    val quantity: Int
)