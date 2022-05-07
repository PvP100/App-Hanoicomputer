package com.example.utt.hnccomputer.base.entity

import com.example.utt.hnccomputer.utils.Define
import com.google.gson.annotations.SerializedName

class BaseObjectLoadMoreResponse<T> : BaseObjectResponse<T> {

    @SerializedName("current_page")
    var currentPage: Int = 0
    @SerializedName("totalPage")
    var totalPage: Int = 0
    var isRefresh: Boolean = false
    var isLoadmore: Boolean = false

    constructor() {

    }

    constructor(
        type: Int,
        data: T?,
        error: Throwable?,
        isRefresh: Boolean,
        isLoadmore: Boolean,
        isShowingError: Boolean = true
    ) : super(type, data, error) {
        this.isRefresh = isRefresh
        this.isLoadmore = isLoadmore
        this.isShowingError = isShowingError
    }

    override fun loading(): BaseObjectLoadMoreResponse<T> {
        return BaseObjectLoadMoreResponse(
            Define.ResponseStatus.LOADING,
            null,
            null,
            isRefresh,
            isLoadmore
        )
    }

    fun success(
        data: T,
        isRefresh: Boolean,
        isLoadmore: Boolean
    ): BaseObjectLoadMoreResponse<T> {
        this.isLoadmore = isLoadmore
        this.isRefresh = isRefresh
        return BaseObjectLoadMoreResponse(
            Define.ResponseStatus.SUCCESS,
            data,
            null,
            isRefresh,
            isLoadmore
        )
    }

    override fun error(throwable: Throwable,isShowingError:Boolean): BaseObjectLoadMoreResponse<T> {
        return BaseObjectLoadMoreResponse(
            Define.ResponseStatus.ERROR,
            null,
            throwable,
            isRefresh,
            isLoadmore,
            isShowingError
        )
    }
}