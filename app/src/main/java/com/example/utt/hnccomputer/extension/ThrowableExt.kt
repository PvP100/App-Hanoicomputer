package com.example.utt.hnccomputer.extension

import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

fun Throwable.getErrorBody(): BaseResponse {
    if (this is HttpException) {
        val body = response().errorBody()
        val gson = Gson()
        val adapter = gson.getAdapter(BaseResponse::class.java)
        try {
            return adapter.fromJson(body?.string())
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return BaseResponse()
}