package com.example.utt.hnccomputer.network.repository

import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.entity.model.Order
import com.example.utt.hnccomputer.entity.model.OrderView
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.extension.backgroundThread
import com.example.utt.hnccomputer.network.ApiInterface
import io.reactivex.Single
import javax.inject.Inject

class OrderRepository @Inject constructor(private val apiInterface: ApiInterface) {

    fun getOrder(type: Int, customerId: String): Single<BaseObjectLoadMoreResponse<ResultResponse<Order>>> {
        return apiInterface.getOrder(customerId, type).backgroundThread()
    }

}