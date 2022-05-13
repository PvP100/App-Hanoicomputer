package com.example.utt.hnccomputer.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.utt.hnccomputer.base.BaseConstraintCustomView
import com.example.utt.hnccomputer.databinding.ViewDetailInformationBinding

class DetailInformationView(context: Context, attrs: AttributeSet? = null) :
    BaseConstraintCustomView<ViewDetailInformationBinding>(context, attrs) {

    override fun getCustomViewBinding(context: Context): ViewDetailInformationBinding {
        return ViewDetailInformationBinding.inflate(LayoutInflater.from(context), this, false)
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun initStyAble() {

    }

    override val styleAble: IntArray?
        get() = null
}