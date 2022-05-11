package com.example.utt.hnccomputer.ui.fragment.register

import android.util.Base64
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.entity.request.RegisterRequest
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: Repository) : BaseViewModel() {

    fun register(
        username: String, password: String
    ) {

        val base64 = "$username:$password"

        mDisposable.add(
            repository.register(
                "Basic ${Base64.encodeToString(base64.toByteArray(), Base64.NO_WRAP)}", RegisterRequest("")
            ).doOnSubscribe {
                _baseResponse.value = BaseResponse().loadingNoData()
            }.subscribe(
                    {
                        _baseResponse.value = BaseResponse().successNoData()
                    },
                    {
                        _baseResponse.value = BaseResponse().errorNoData(it)
                    }
                )
        )
    }

}