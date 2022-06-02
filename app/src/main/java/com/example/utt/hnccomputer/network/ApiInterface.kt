package com.example.utt.hnccomputer.network

import com.example.utt.hnccomputer.base.entity.BaseListResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.entity.LoginResponse
import com.example.utt.hnccomputer.entity.model.*
import com.example.utt.hnccomputer.entity.request.ChangePasswordRequest
import com.example.utt.hnccomputer.entity.request.OrderBody
import com.example.utt.hnccomputer.entity.request.RegisterRequest
import com.example.utt.hnccomputer.entity.response.ResultResponse
import io.reactivex.Single
import okhttp3.MultipartBody
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
    fun login(@Body fcmToken: String = "", @Header("Authorization") auth: String) : Single<BaseObjectResponse<LoginResponse>>

    @GET("${URL}/api/customers/profiles/{customerId}")
    fun getCustomerInformation(@Path("customerId") customerId: String): Single<BaseObjectResponse<Customer>>

    @POST("${URL}/api/auths/customer/{customerID}/newPassword")
    fun changePassword(@Path("customerID") customerId: String, @Body changePasswordRequest: ChangePasswordRequest): Single<BaseResponse>

    @PUT("${URL}/api/customers/updateProfile/{id}")
    fun editCustomer(@Path("id") id: String, @Query("updateType") updateType: Int, @Query("updateProfile") updateProfile: String): Single<BaseObjectResponse<Customer>>

    @POST("${URL}/api/auths/customers/register")
    @Headers("Content-Type: application/json")
    fun register(@Header("Authorization") auth: String, @Body registerRequest: RegisterRequest) : Single<BaseResponse>

    @GET("${URL}/api/products/getProduct")
    fun getProduct(@Query("categoryId") categoryId: Int?, @Query("brandId") brandId: Int?, @Query("sortBy") sort: String, @Query("sortType") type: String, @Query("isSale") sale: Int): Single<BaseObjectLoadMoreResponse<ResultResponse<Product>>>

    @GET("${URL}/api/products/searchProduct/{productName}")
    fun searchProduct(@Path("productName") productName: String?): Single<BaseObjectLoadMoreResponse<ResultResponse<Product>>>

    @GET("${URL}/api/products/product/{id}")
    fun getProductDetail(@Path("id") productId: String): Single<BaseObjectResponse<Product>>

    @GET("${URL}/api/order/customerorder/{customerId}")
    fun getOrder(@Path("customerId") customerId: String, @Query("type") type: Int): Single<BaseObjectLoadMoreResponse<ResultResponse<Order>>>

    @GET("${URL}/api/order/getdetail/{id}")
    fun getOrderDetail(@Path("id") orderId: Int): Single<BaseObjectResponse<OrderDetail>>

    @POST("${URL}/api/order/addorder")
    fun createOrder(@Body myOrderInformation: OrderBody): Single<BaseResponse>

    @Multipart
    @POST("${URL}/api/customers/uploadavatar/{customerId}")
    fun uploadAvatar(@Path("customerId") customerId: String, @Part customerAvatar: MultipartBody.Part): Single<BaseResponse>

}
