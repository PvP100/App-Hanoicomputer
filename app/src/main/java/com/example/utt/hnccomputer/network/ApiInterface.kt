package com.example.utt.hnccomputer.network

import com.example.utt.hnccomputer.base.entity.BaseListResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse
import com.example.utt.hnccomputer.entity.LoginResponse
import com.example.utt.hnccomputer.entity.model.*
import com.example.utt.hnccomputer.entity.response.ResultResponse
import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    companion object {
        const val URL = "hnc-utt-0.0.1-SNAPSHOT"
    }

    @GET("${URL}/api/banner/getBanner")
    fun getBanner(): Single<BaseListResponse<Banner>>

    @GET("${URL}/api/brand/getBrand")
    fun getBrand(): Single<BaseObjectLoadMoreResponse<ResultResponse<Brand>>>

    @GET("${URL}/api/category/getHomeCategory")
    fun getHomeCategory(): Single<BaseListResponse<HomeCategory>>

    @GET("${URL}/api/category/categories")
    fun getCategory(): Single<BaseObjectLoadMoreResponse<ResultResponse<Category>>>

    @POST("${URL}/api/auths/customer/login")
    @Headers("Content-Type: application/json")
    fun login(@Body fcmToken: String, @Header("Authorization") auth: String) : Single<BaseObjectResponse<LoginResponse>>

    @GET("${URL}/api/products/getProduct")
    fun getProduct(@Query("categoryId") categoryId: Int?, @Query("brandId") brandId: Int?): Single<BaseObjectLoadMoreResponse<ResultResponse<Product>>>
}
