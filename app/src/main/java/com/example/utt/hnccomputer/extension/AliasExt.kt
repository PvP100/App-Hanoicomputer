package com.example.utt.hnccomputer.extension

import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.entity.BaseListLoadMoreResponse
import com.example.utt.hnccomputer.base.entity.BaseListResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse

typealias ObjectResponse<T> = MutableLiveData<BaseObjectResponse<T>>
typealias ListResponse<T> = MutableLiveData<BaseListResponse<T>>
typealias ListLoadMoreResponse<T> = MutableLiveData<BaseListLoadMoreResponse<T>>

typealias AndroidColors = android.R.color
typealias ProjectColors = R.color
