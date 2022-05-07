package com.example.utt.hnccomputer.network


import com.example.utt.hnccomputer.base.entity.BaseListResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.base.entity.BaseObjectResponse
import com.example.utt.hnccomputer.entity.LoginRequest
import com.example.utt.hnccomputer.entity.LoginResponse
import com.example.utt.hnccomputer.entity.model.Banner
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.entity.model.Category
import com.example.utt.hnccomputer.entity.model.HomeCategory
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.extension.backgroundThread
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(private val apiInterface: ApiInterface, val localAssetInterface: LocalAssetInterface) {

    fun login(username:String,password:String): Single<BaseObjectResponse<LoginResponse>> {
        val loginRequest = LoginRequest()
        loginRequest.username = username
        loginRequest.password = password
        return apiInterface.login(loginRequest.toString(), "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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