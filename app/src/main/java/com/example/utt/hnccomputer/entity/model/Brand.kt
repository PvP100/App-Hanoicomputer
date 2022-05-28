package com.example.utt.hnccomputer.entity.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Brand(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imgUrl")
    val imgUrl: String,
    @SerializedName("brandName")
    val brandName: String,
    val brandTotalCount: Int,
    var isSelected: Boolean = false
) : Serializable