package com.example.utt.hnccomputer.ui.fragment.my_order

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.entity.model.Order
import com.example.utt.hnccomputer.entity.model.OrderStatus
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.network.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderByTypeViewModel @Inject constructor(private val orderRepository: OrderRepository, private val sharedPreferences: SharedPreferences) : BaseViewModel() {

    var type = OrderStatus.CHECK

    private val _order: MutableLiveData<BaseObjectLoadMoreResponse<ResultResponse<Order>>> = MutableLiveData()
    val order: LiveData<BaseObjectLoadMoreResponse<ResultResponse<Order>>> = _order

    fun getOrder() {
        mDisposable.add(
            orderRepository.getOrder(type.type, sharedPreferences.getString("customerId", "") ?: "")
                .doOnSubscribe {
                    _order.value = BaseObjectLoadMoreResponse<ResultResponse<Order>>().loading()
                }
                .subscribe(
                    {
                        _order.value = it.data?.let { it1 ->
                            BaseObjectLoadMoreResponse<ResultResponse<Order>>().success(
                                it1, isRefresh = false, isLoadmore = false)
                        }
                    },
                    {
                        _order.value = BaseObjectLoadMoreResponse<ResultResponse<Order>>().error(it)
                    }
                )
        )
    }

}