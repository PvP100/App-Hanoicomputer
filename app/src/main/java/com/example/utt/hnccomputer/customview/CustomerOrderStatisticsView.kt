package com.example.utt.hnccomputer.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.utt.hnccomputer.base.BaseConstraintCustomView
import com.example.utt.hnccomputer.databinding.ViewCustomerOrderStatisticsBinding
import com.example.utt.hnccomputer.entity.model.OrderView

class CustomerOrderStatisticsView(context: Context, attrs: AttributeSet? = null) :
    BaseConstraintCustomView<ViewCustomerOrderStatisticsBinding>(context, attrs) {
    override fun getCustomViewBinding(context: Context): ViewCustomerOrderStatisticsBinding {
        return ViewCustomerOrderStatisticsBinding.inflate(LayoutInflater.from(context), this, false)
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun initStyAble(a: TypedArray) {

    }

    fun setOrder(order: OrderView?) {
        order?.let {
            binding.order = order
        }
    }

    override val styleAble: IntArray?
        get() = null
}