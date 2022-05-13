package com.example.utt.hnccomputer.network


import com.example.utt.hnccomputer.base.entity.BaseListResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.entity.LoginRequest
import com.example.utt.hnccomputer.entity.LoginResponse
import com.example.utt.hnccomputer.entity.model.*
import com.example.utt.hnccomputer.entity.request.ChangePasswordRequest
import com.example.utt.hnccomputer.entity.request.RegisterRequest
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.extension.backgroundThread
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface: ApiInterface) {

    fun login(author: String): Single<BaseObjectResponse<LoginResponse>> {
        return apiInterface.login(auth = author).backgroundThread()
    }

    fun getCustomerInformation(customerId: String): Single<BaseObjectResponse<Customer>> {
        return apiInterface.getCustomerInformation(customerId).backgroundThread()
    }

    fun changePassword(customerId: String, changePasswordRequest: ChangePasswordRequest): Single<BaseResponse> {
        return apiInterface.changePassword(customerId, changePasswordRequest).backgroundThread()
    }

    fun register(author: String, registerRequest: RegisterRequest): Single<BaseResponse> {
        return apiInterface.register(author, registerRequest).backgroundThread()
    }

    fun getBrand(): Single<BaseObjectLoadMoreResponse<ResultResponse<Brand>>> {
        return apiInterface.getBrand().backgroundThread()
    }

    fun getHomeCategory(): Single<BaseListResponse<HomeCategory>> {
        return apiInterface.getHomeCategory().backgroundThread()
    }

    fun getCategory(): Single<BaseObjectLoadMoreResponse<ResultResponse<Category>>> {
        return apiInterface.getCategory().backgroundThread()
    }

    fun getBanner(): Single<BaseListResponse<Banner>> {
        return apiInterface.getBanner().backgroundThread()
    }
}