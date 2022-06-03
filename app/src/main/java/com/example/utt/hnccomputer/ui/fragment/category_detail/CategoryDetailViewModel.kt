package com.example.utt.hnccomputer.ui.fragment.category_detail

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseError
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.database.repository.MyOrderRepository
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.network.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(private val sharedPreferences: SharedPreferences, private val productRepository: ProductRepository, private val myOrderRepository: MyOrderRepository) : BaseViewModel() {

    var categoryId = 0

    var brandId = 0

    var sortBy: String = "createdDate"

    var isSale = 0

    var sortType: String = "desc"

    private val _product: MutableLiveData<BaseObjectLoadMoreResponse<ResultResponse<Product>>> = MutableLiveData()
    val product: LiveData<BaseObjectLoadMoreResponse<ResultResponse<Product>>> = _product

    fun getProduct(isRefresh: Boolean = false) {
        mDisposable.add(
            productRepository.getProduct(categoryId, brandId, sortBy, sortType, isSale)
                .doOnSubscribe {
                    _product.value = BaseObjectLoadMoreResponse<ResultResponse<Product>>().loading()
                }.subscribe(
                    {
                        _product.value = it.data?.let { it1 ->
                            BaseObjectLoadMoreResponse<ResultResponse<Product>>().success(
                                it1, isRefresh = isRefresh, isLoadmore = false
                            )
                        }
                    },
                    {
                        _product.value = BaseObjectLoadMoreResponse<ResultResponse<Product>>().error(it)
                    }
                )
        )
    }

    fun checkLogin(): Boolean {
        return sharedPreferences.getBoolean("loginSave", false)
    }

    fun addToCart(product: Product) {
        if (product.quantity <= 0) {
            _baseResponse.value = BaseResponse().errorNoData(BaseError("Sản phẩm này hiện tại đang hết hàng"))
            return
        }
        mDisposable.add(
            myOrderRepository.isExists(product.id).flatMap {
                if (!it) {
                    myOrderRepository.insertProduct(
                        MyOrderInformation(
                            product.price,
                            product.id,
                            1,
                            productName = product.name,
                            imgUrl = product.logoUrl,
                            totalQuantity = product.quantity
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
                } else {
                    _baseResponse.value = BaseResponse().errorNoData(BaseError("Sản phẩm này đã có trong giỏ hàng"))
                }
                Single.just(BaseResponse())
            }.subscribe()
        )
    }
}