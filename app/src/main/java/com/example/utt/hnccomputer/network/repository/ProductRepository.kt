package com.example.utt.hnccomputer.network.repository

import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.extension.backgroundThread
import com.example.utt.hnccomputer.network.ApiInterface
import io.reactivex.Single
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiInterface: ApiInterface) {

    fun getProduct(categoryId: Int? = null, brandId: Int? = null, sortBy: String = "createdDate", sortType: String = "desc", isSale: Int = 0) : Single<BaseObjectLoadMoreResponse<ResultResponse<Product>>> {
        return apiInterface.getProduct(categoryId, brandId, sortBy, sortType, isSale).backgroundThread()
    }

    fun getSearch(value: String): Single<BaseObjectLoadMoreResponse<ResultResponse<Product>>> {
        return apiInterface.searchProduct(value).backgroundThread()
    }

    fun getDetail(productId: String): Single<BaseObjectResponse<Product>> {
        return apiInterface.getProductDetail(productId).backgroundThread()
    }

}