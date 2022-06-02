package com.example.utt.hnccomputer.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.utt.hnccomputer.base.BaseConstraintCustomView
import com.example.utt.hnccomputer.databinding.ViewCustomerOrderStatisticsBinding

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

    override val styleAble: IntArray?
        get() = null
}