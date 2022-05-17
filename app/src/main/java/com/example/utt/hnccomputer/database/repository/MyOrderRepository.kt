package com.example.utt.hnccomputer.database.repository

import com.example.utt.hnccomputer.database.dao.MyOrderDao
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.extension.backgroundThread
import javax.inject.Inject

class MyOrderRepository @Inject constructor(private val myOrderDao: MyOrderDao) {

    fun insertProduct(myOrderInformation: MyOrderInformation) = myOrderDao.insertProduct(myOrderInformation).backgroundThread()

    fun removeProduct(productId: String) = myOrderDao.deleteProduct(productId).backgroundThread()

    fun getOrder() = myOrderDao.getAll().backgroundThread()

    fun isExists(productId: String) = myOrderDao.isProductExists(productId = productId).backgroundThread()

    fun deleteAll() = myOrderDao.deleteAll().backgroundThread()

}