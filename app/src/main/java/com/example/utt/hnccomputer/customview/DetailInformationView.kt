package com.example.utt.hnccomputer.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.utt.hnccomputer.R
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

    override val styleAble: IntArray?
        get() = R.styleable.DetailInformationView

    override fun initStyAble(a: TypedArray) {
        if (a.hasValue(R.styleable.DetailInformationView_header_title_information)) {
            binding.tvTitle.text = a.getString(R.styleable.DetailInformationView_header_title_information)
        }
        if (a.hasValue(R.styleable.DetailInformationView_detail_left_drawable)) {
            binding.icDrawable.setImageResource(a.getResourceId(R.styleable.DetailInformationView_detail_left_drawable, 0))
        }
    }

    fun setDetailInformation(information: String?) {
        binding.detail.text = information
    }
}