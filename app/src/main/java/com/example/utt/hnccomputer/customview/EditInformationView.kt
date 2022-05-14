package com.example.utt.hnccomputer.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.doAfterTextChanged
import com.example.utt.hnccomputer.base.BaseConstraintCustomView
import com.example.utt.hnccomputer.databinding.ViewEditInformationBinding
import com.example.utt.hnccomputer.extension.gone
import com.example.utt.hnccomputer.extension.visible

class EditInformationView(context: Context, attrs: AttributeSet? = null) :
    BaseConstraintCustomView<ViewEditInformationBinding>(context, attrs) {
    override fun getCustomViewBinding(context: Context): ViewEditInformationBinding {
        return ViewEditInformationBinding.inflate(LayoutInflater.from(context), this, false)
    }

    override fun initListener() {
        binding.apply {
            edtInput.doAfterTextChanged {
                if (binding.edtInput.text.isNullOrEmpty()) {
                    binding.icClearText.gone()
                } else {
                    binding.icClearText.visible()
                }
            }
            icClearText.setOnClickListener {
                binding.edtInput.text = null
            }
        }
    }

    override fun initStyAble(a: TypedArray) {

    }

    override val styleAble: IntArray?
        get() = null

    fun setInformation(information: String?) {
        binding.edtInput.setText(information)
    }

    fun getInformation() = binding.edtInput.text.toString().trim()
}