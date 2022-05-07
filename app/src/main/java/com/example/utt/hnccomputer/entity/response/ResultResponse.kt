package com.example.utt.hnccomputer.entity.response

import com.google.gson.annotations.SerializedName

data class ResultResponse<T>(
    val results: List<T>,
    val totalItem: Int,
    @SerializedName("pageIndex")
    var currentPage: Int = 0,
    @SerializedName("totalPage")
    var totalPage: Int = 0,
)