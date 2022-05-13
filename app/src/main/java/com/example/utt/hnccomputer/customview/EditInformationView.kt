package com.example.utt.hnccomputer.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.utt.hnccomputer.base.BaseConstraintCustomView
import com.example.utt.hnccomputer.databinding.ViewEditInformationBinding

class EditInformationView(context: Context, attrs: AttributeSet? = null) :
    BaseConstraintCustomView<ViewEditInformationBinding>(context, attrs) {
    override fun getCustomViewBinding(context: Context): ViewEditInformationBinding {
        return ViewEditInformationBinding.inflate(LayoutInflater.from(context), this, false)
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun initStyAble(a: TypedArray) {

    }

    override val styleAble: IntArray?
        get() = null

    fun getPassword() = binding.edtInput.text.toString().trim()
}