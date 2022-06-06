package com.example.utt.hnccomputer.ui.fragment.home

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.entity.BaseError
import com.example.utt.hnccomputer.base.entity.BaseListResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.database.repository.MyOrderRepository
import com.example.utt.hnccomputer.entity.model.Banner
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.entity.model.HomeCategory
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val myOrderRepository: MyOrderRepository,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {

    val brand = MutableLiveData<BaseObjectLoadMoreResponse<ResultResponse<Brand>>>()
    val homeCategory = MutableLiveData<BaseListResponse<HomeCategory>>()
    val banner = MutableLiveData<BaseListResponse<Banner>>()

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
                            if (product.isSale == 1) product.salePrice else product.price,
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

    fun checkLogin(): Boolean {
        return sharedPreferences.getBoolean("loginSave", false)
    }

    fun getHome(isRefresh: Boolean = false) {
        mDisposable.add(
            Single.merge(repository.getBanner(), repository.getBrand(), repository.getHomeCategory()).doOnSubscribe {
                _baseResponse.value = BaseResponse().loadingNoData().apply { this.isRefreshNoResponse =  isRefresh }
            }.doOnComplete {
                _baseResponse.value = BaseResponse().successNoData()
            }.subscribe(
                {
                    if (it is BaseListResponse<*>) {
                        if (it.data is List<*>) {
                            if (it.data.first() is HomeCategory) {
                                homeCategory.value = it as BaseListResponse<HomeCategory>
                            } else if (it.data.first() is Banner) {
                                banner.value = it as BaseListResponse<Banner>
                            }
                        }
                    } else if (it is BaseObjectLoadMoreResponse<*>) {
                        if (it.data is ResultResponse<*>) {
                            if (it.data.results.first() is Brand) {
                                brand.value = it as BaseObjectLoadMoreResponse<ResultResponse<Brand>>
                            }
                        }
                    }
                },
                {
                    _baseResponse.value = BaseResponse().errorNoData(it)
                }
            )
        )
    }

}