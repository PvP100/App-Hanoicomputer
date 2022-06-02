package com.example.utt.hnccomputer.ui.fragment.login

import android.content.SharedPreferences
import android.util.Base64
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseError
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.extension.setBoolean
import com.example.utt.hnccomputer.extension.setString
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository, private val sharedPreferences: SharedPreferences) : BaseViewModel() {

    fun login(username: String, password: String) {
        val base64 = "$username:$password"

        if (username.isEmpty()) {
            _baseResponse.value = BaseResponse().errorNoData(BaseError("Vui lòng nhập tên đăng nhập"))
            return
        }
        if (password.isEmpty()) {
            _baseResponse.value = BaseResponse().errorNoData(BaseError("Vui lòng nhập mật khẩu"))
            return
        }

        mDisposable.add(
            repository.login("Basic ${Base64.encodeToString(base64.toByteArray(), Base64.NO_WRAP)}")
                .doOnSubscribe {
                    _baseResponse.value = BaseResponse().loadingNoData()
                }
                .subscribe(
                    {
                        sharedPreferences.setBoolean("loginSave", true)
                        it.data?.customerId?.let { it1 ->
                            sharedPreferences.setString("customerId",
                                it1
                            )
                        }
                        _baseResponse.value = BaseResponse().successNoData()
                    },
                    {
                        _baseResponse.value = BaseResponse().errorNoData(it)
                    }
                )
        )
    }

}