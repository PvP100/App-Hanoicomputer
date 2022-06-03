package com.example.utt.hnccomputer.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_order")
data class MyOrderInformation(
    @ColumnInfo(name = "price")
    val price: Long,

    @ColumnInfo(name = "product_id")
    @PrimaryKey
    val productId: String,

    @ColumnInfo(name = "quantity")
    var quantity: Int,

    @ColumnInfo(name = "totalQuantity")
    var totalQuantity: Int,

    @ColumnInfo(name = "product_name")
    val productName: String,

    @ColumnInfo(name = "img_url")
    val imgUrl: String
)