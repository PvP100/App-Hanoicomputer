package com.example.utt.hnccomputer.ui.fragment.account_information

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.entity.model.Customer
import com.example.utt.hnccomputer.entity.model.FilterOption
import com.example.utt.hnccomputer.entity.model.Gender
import com.example.utt.hnccomputer.entity.request.ChangePasswordRequest
import com.example.utt.hnccomputer.extension.getString
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountInformationViewModel @Inject constructor(private val sharedPreferences: SharedPreferences, private val repository: Repository) : BaseViewModel() {

    var editType = 0

    private val _customerResponse: MutableLiveData<BaseObjectResponse<Customer>> = MutableLiveData()
    val customerResponse: LiveData<BaseObjectResponse<Customer>> = _customerResponse

    private val _listFilter: MutableLiveData<List<FilterOption>> = MutableLiveData()
    val listFilter: LiveData<List<FilterOption>> = _listFilter

    init {
        _listFilter.value = listOf(
            FilterOption(1, Gender.Nam.genderName, true),
            FilterOption(0, Gender.Nữ.genderName)
        )
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

    fun editCustomerInformation(updateProfile: String) {
        mDisposable.add(
            repository.editCustomer(
                sharedPreferences.getString("customerId"),
                editType,
                updateProfile
            ).doOnSubscribe {
                _customerResponse.value = BaseObjectResponse<Customer>().loading()
            }.subscribe(
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
            )
        )
    }

    fun changePassword(newPassword: String, oldPassword: String) {
        mDisposable.add(repository.changePassword(
            sharedPreferences.getString("customerId"), ChangePasswordRequest(
                oldPassword, newPassword
            )
        ).doOnSubscribe {
            _baseResponse.value = BaseResponse().loadingNoData()
        }.subscribe(
            {
                _baseResponse.value = BaseResponse().successNoData()
            },
            {
                _baseResponse.value = BaseResponse().errorNoData(it)
            }
        ))
    }

}