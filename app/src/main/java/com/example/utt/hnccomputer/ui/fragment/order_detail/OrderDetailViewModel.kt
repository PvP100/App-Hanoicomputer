package com.example.utt.hnccomputer.ui.fragment.order_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse
import com.example.utt.hnccomputer.entity.model.OrderDetail
import com.example.utt.hnccomputer.network.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderDetailViewModel @Inject constructor(private val orderRepository: OrderRepository) : BaseViewModel() {

    private val _orderDetail: MutableLiveData<BaseObjectResponse<OrderDetail>> = MutableLiveData()
    val orderDetail: LiveData<BaseObjectResponse<OrderDetail>> = _orderDetail

    private val _cancelDate: MutableLiveData<BaseObjectResponse<Long>> = MutableLiveData()
    val cancelDate: LiveData<BaseObjectResponse<Long>> = _cancelDate

    var orderId = 0

    fun getOrderDetail() {
        mDisposable.add(
            orderRepository.getOrderDetail(orderId)
                .doOnSubscribe {
                    _orderDetail.value = BaseObjectResponse<OrderDetail>().loading()
                }
                .subscribe(
                    {
                        _orderDetail.value = it.data?.let { it1 ->
                            BaseObjectResponse<OrderDetail>().success(
                                it1
                            )
                        }
                    },
                    {
                        _orderDetail.value = BaseObjectResponse<OrderDetail>().error(it)
                    }
                )
        )
    }

    fun cancelOrder() {
        mDisposable.add(
            orderRepository.cancelOrder(orderId)
                .doOnSubscribe {
                    _cancelDate.value = BaseObjectResponse<Long>().loading()
                }.subscribe(
                    {
                        _cancelDate.value = it.data?.let { it1 ->
                            BaseObjectResponse<Long>().success(
                                it1
                            )
                        }
                    },
                    {
                        _cancelDate.value = BaseObjectResponse<Long>().error(it)
                    }
                )
        )
    }

}