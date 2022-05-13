package com.example.utt.hnccomputer.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseConstraintCustomView
import com.example.utt.hnccomputer.databinding.ViewRegisterInputBinding

class RegisterInputView(context: Context, attrs: AttributeSet? = null) :
    BaseConstraintCustomView<ViewRegisterInputBinding>(context, attrs) {
    override fun getCustomViewBinding(context: Context): ViewRegisterInputBinding {
        return ViewRegisterInputBinding.inflate(LayoutInflater.from(context), this, false)
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun initStyAble(a: TypedArray) {
        if (a.hasValue(R.styleable.RegisterInputView_input_type)) {

        }
        if (a.hasValue(R.styleable.RegisterInputView_header_title_input)) {
            binding.tvTitle.text = a.getString(R.styleable.RegisterInputView_header_title_input)
        }
    }

    fun getText() = binding.edtInput.text.toString().trim()

    override val styleAble: IntArray?
        get() = R.styleable.RegisterInputView
}