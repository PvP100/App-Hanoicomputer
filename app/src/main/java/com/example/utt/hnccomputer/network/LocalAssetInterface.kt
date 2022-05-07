package com.example.utt.hnccomputer.network

import com.example.utt.hnccomputer.entity.response.HomeBrandResponse
import com.example.utt.hnccomputer.extension.backgroundThread
import com.example.utt.hnccomputer.utils.Utils
import io.reactivex.Single
import javax.inject.Inject

interface LocalAssetInterface {

    fun getBrand(): Single<HomeBrandResponse>

    class LocalAssetInterfaceImpl @Inject constructor(): LocalAssetInterface {
        override fun getBrand(): Single<HomeBrandResponse> {
            return Single.fromCallable { Utils.getObjectDataFromFile("home_list_brand.json", HomeBrandResponse::class.java) }.backgroundThread()
        }
    }

}