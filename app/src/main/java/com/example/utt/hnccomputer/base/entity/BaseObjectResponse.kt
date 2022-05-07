package com.example.utt.hnccomputer.base.entity

import com.example.utt.hnccomputer.utils.Define
import com.google.gson.annotations.SerializedName

open class BaseObjectResponse<T>(
    val type: Int = 0,
    @SerializedName("data")
    val data: T? = null,
    val error: Throwable? = null,
    var isShowingError:Boolean = true
) : BaseResponse() {
    open fun loading(): BaseObjectResponse<T>? {
        return BaseObjectResponse(Define.ResponseStatus.LOADING,null,null)
    }

    fun success(data: T): BaseObjectResponse<T> {
        return BaseObjectResponse(Define.ResponseStatus.SUCCESS,data,null)
    }

    open fun error(throwable: Throwable, isShowingError:Boolean = true): BaseObjectResponse<T> {
        return BaseObjectResponse(Define.ResponseStatus.ERROR,null,throwable,isShowingError)
    }
}