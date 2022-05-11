package com.example.utt.hnccomputer.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.utt.hnccomputer.base.entity.BaseResponse
import io.reactivex.disposables.CompositeDisposable


open class BaseViewModel : ViewModel() {

    protected var mDisposable = CompositeDisposable()

    protected val _baseResponse: MutableLiveData<BaseResponse> = MutableLiveData()
    val response: LiveData<BaseResponse> = _baseResponse

    override fun onCleared() {
        super.onCleared()
        mDisposable.clear()
    }
}
