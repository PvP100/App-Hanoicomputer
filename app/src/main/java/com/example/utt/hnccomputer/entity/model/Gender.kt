package com.example.utt.hnccomputer.entity.model

import com.google.gson.annotations.SerializedName

enum class Gender(val type: Int, val genderName: String) {

    @SerializedName("1")
    Nam(1, "Nam"),

    @SerializedName("0")
    Nữ(0, "Nữ");

    override fun toString(): String {
        return genderName
    }
}