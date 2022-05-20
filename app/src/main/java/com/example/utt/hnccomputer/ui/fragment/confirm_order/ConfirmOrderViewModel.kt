package com.example.utt.hnccomputer.ui.fragment.confirm_order

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.database.repository.MyOrderRepository
import com.example.utt.hnccomputer.entity.model.Customer
import com.example.utt.hnccomputer.extension.getString
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import javax.inject.Inject

@HiltViewModel
class ConfirmOrderViewModel @Inject constructor(private val repository: Repository, private val myOrderRepository: MyOrderRepository, private val sharedPreferences: SharedPreferences) : BaseViewModel() {

    private val _myOrder: MutableLiveData<List<MyOrderInformation>> = MutableLiveData()
    val myOrder: LiveData<List<MyOrderInformation>> = _myOrder

    private val _customerResponse: MutableLiveData<BaseObjectResponse<Customer>> = MutableLiveData()
    val customerResponse: LiveData<BaseObjectResponse<Customer>> = _customerResponse

    fun getOrder() {
        mDisposable.add(
            Single.merge(repository.getCustomerInformation(sharedPreferences.getString("customerId")), myOrderRepository.getOrder())
                .doOnSubscribe {
                    _baseResponse.value = BaseResponse().loadingNoData()
                }
                .doOnComplete {
                    _baseResponse.value = BaseResponse().successNoData()
                }
                .doOnError {
                    _baseResponse.value = BaseResponse().errorNoData(it)
                }
                .subscribe(
                    {
                        Log.v("phongpv", it.toString())
                        if (it is List<*>) {
                            if (it.first() is MyOrderInformation) {
                                _myOrder.value = it as List<MyOrderInformation>
                            }
                        } else if (it is BaseObjectResponse<*>) {
                            if (it.data is Customer) {
                                _customerResponse.value = it as BaseObjectResponse<Customer>
                            }
                        }
                    },
                    {
                    }
                )
        )
    }

}