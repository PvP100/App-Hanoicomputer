package com.example.utt.hnccomputer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.utt.hnccomputer.base.entity.BaseResponse
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MyOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(myOrder: MyOrderInformation): Completable

    @Query("delete from my_order")
    fun deleteAll(): Completable

    @Query("update my_order set quantity = :quantity where product_id = :productId")
    fun updateQuantity(quantity: Int, productId: String): Single<Int>

    @Query("select exists (select 1 from my_order where product_id = :productId)")
    fun isProductExists(productId: String?): Single<Boolean>

    @Query("select * from my_order")
    fun getAll(): Single<List<MyOrderInformation>>

    @Query("delete from my_order where product_id = :productId")
    fun deleteProduct(productId: String): Single<Int>

}