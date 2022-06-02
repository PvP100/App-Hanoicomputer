package com.example.utt.hnccomputer.ui.fragment.product_detail

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseError
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.database.repository.MyOrderRepository
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.network.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val productRepository: ProductRepository,
                                                 private val myOrderRepository: MyOrderRepository,
                                                 private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    private val _product: MutableLiveData<BaseObjectResponse<Product>> = MutableLiveData()
    val product: LiveData<BaseObjectResponse<Product>> = _product

    fun getProductDetail(productId: String) {
        mDisposable.add(
            productRepository.getDetail(
                productId
            ).doOnSubscribe {
                _product.value = BaseObjectResponse<Product>().loading()
            }.subscribe(
                {
                    _product.value = it.data?.let { it1 -> BaseObjectResponse<Product>().success(it1) }
                },
                {
                    _product.value = BaseObjectResponse<Product>().error(it)
                }
            )
        )
    }

    fun checkLogin(): Boolean {
        return sharedPreferences.getBoolean("loginSave", false)
    }

    fun addToCart() {
        mDisposable.add(
            myOrderRepository.isExists(_product.value?.data?.id).flatMap {
                if (!it) {
                    _product.value?.data?.let { product ->
                        myOrderRepository.insertProduct(
                            MyOrderInformation(
                                product.price,
                                product.id,
                                1,
                                productName = product.name,
                                imgUrl = product.logoUrl
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
                        )
                    }
                } else {
                    _baseResponse.value = BaseResponse().errorNoData(BaseError("Sản phẩm này đã có trong giỏ hàng"))
                }
                Single.just(BaseResponse())
            }.subscribe()
        )
    }
}