package com.example.utt.hnccomputer.base.entity

import com.example.utt.hnccomputer.utils.Define
import com.google.gson.annotations.SerializedName

open class BaseResponse(
    val typeBase: Int = 0,
    val errorNoResponse: Throwable? = null,
    var isRefreshNoResponse: Boolean = false
){
    @SerializedName("code") val status: Int? = null
    @SerializedName("msg") val msg: String? = null

    fun loadingNoData(): BaseResponse {
        return BaseResponse(Define.ResponseStatus.LOADING, isRefreshNoResponse = isRefreshNoResponse)
    }

    fun errorNoData(errorNoResponse: Throwable?): BaseResponse {
        return BaseResponse(Define.ResponseStatus.ERROR, errorNoResponse, isRefreshNoResponse)
    }

    fun successNoData(): BaseResponse {
        return BaseResponse(Define.ResponseStatus.SUCCESS, isRefreshNoResponse =  isRefreshNoResponse)
    }
}