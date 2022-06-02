package com.example.utt.hnccomputer.ui.fragment.account

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.entity.model.Customer
import com.example.utt.hnccomputer.extension.clearAll
import com.example.utt.hnccomputer.extension.convertToMultipartBody
import com.example.utt.hnccomputer.extension.getString
import com.example.utt.hnccomputer.extension.setBoolean
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val repository: Repository
) : BaseViewModel() {

    var avatarPath: String = ""

    private val _customerResponse: MutableLiveData<BaseObjectResponse<Customer>> = MutableLiveData()
    val customerResponse: LiveData<BaseObjectResponse<Customer>> = _customerResponse

    fun logout() {
        sharedPreferences.clearAll()
    }

    fun getCustomerInformation() {
        mDisposable.add(repository.getCustomerInformation(sharedPreferences.getString("customerId"))
            .doOnSubscribe {
                _customerResponse.value = BaseObjectResponse<Customer>().loading()
            }
            .subscribe(
                {
                    _customerResponse.value = it.data?.let { it1 ->
                        BaseObjectResponse<Customer>().success(
                            it1
                        )
                    }
                },
                {
                    _customerResponse.value = BaseObjectResponse<Customer>().error(it)
                }
            ))
    }

    fun uploadAvatar() {
        repository.uploadAvatar(sharedPreferences.getString("customerId"), avatarPath.convertToMultipartBody("avatar"))
            .doOnSubscribe {
                _baseResponse.value = BaseResponse().loadingNoData()
            }.subscribe(
                {
                    _baseResponse.value = BaseResponse().successNoData()
                },
                {
                    _baseResponse.value = BaseResponse().errorNoData(it)
                }
            )
    }

}