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

    fun getOrderDetail(id: Int) {
        mDisposable.add(
            orderRepository.getOrderDetail(id)
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

}