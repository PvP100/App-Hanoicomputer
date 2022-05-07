package com.example.utt.hnccomputer.base.entity

import com.example.utt.hnccomputer.utils.Define
import com.google.gson.annotations.SerializedName

open class BaseResponse(
    val typeBase: Int = 0
){
    @SerializedName("code") val status: Int? = null
    @SerializedName("msg") val msg: String? = null

    fun loadingNoData(): BaseResponse {
        return BaseResponse(Define.ResponseStatus.LOADING)
    }

    fun errorNoData(): BaseResponse {
        return BaseResponse(Define.ResponseStatus.ERROR)
    }

    fun successNoData(): BaseResponse {
        return BaseResponse(Define.ResponseStatus.SUCCESS)
    }
}