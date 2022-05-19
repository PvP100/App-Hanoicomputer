package com.example.utt.hnccomputer.ui.fragment.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.database.repository.MyOrderRepository
import com.example.utt.hnccomputer.extension.ListResponse
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: Repository, private val myOrderRepository: MyOrderRepository) : BaseViewModel() {

    private val _myOrder: MutableLiveData<List<MyOrderInformation>> = MutableLiveData()
    val myOrder: LiveData<List<MyOrderInformation>> = _myOrder

    var position = 0

    fun getCart() {
        mDisposable.add(
            myOrderRepository.getOrder().subscribe(
                {
                    _myOrder.value = it
                },
                {

                }
            )
        )
    }

    fun updateCart(quantity: Int, productId: String) {
        mDisposable.add(
            myOrderRepository.updateQuantity(productId, quantity).subscribe(
                {
                    _baseResponse.value = BaseResponse().successNoData()
                },
                {
                    _baseResponse.value = BaseResponse().errorNoData(it)
                }
            )
        )
    }

    fun removeCart(productId: String) {
        mDisposable.add(
            myOrderRepository.removeProduct(productId).subscribe(
                {
                    _baseResponse.value = BaseResponse().successNoData()
                },
                {

                }
            )
        )
    }

}