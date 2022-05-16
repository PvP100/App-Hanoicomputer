package com.example.utt.hnccomputer.ui.fragment.my_order

import android.content.SharedPreferences
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.entity.model.OrderType
import com.example.utt.hnccomputer.network.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderByTypeViewModel @Inject constructor(private val orderRepository: OrderRepository, private val sharedPreferences: SharedPreferences) : BaseViewModel() {

    var type = OrderType.CHECK

    fun getOrder() {
        mDisposable.add(
            orderRepository.getOrder(type.type, sharedPreferences.getString("customerId", "") ?: "")
                .doOnSubscribe {

                }
                .subscribe(
                    {

                    },
                    {

                    }
                )
        )
    }

}