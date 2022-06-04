package com.example.utt.hnccomputer.customview

import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.widget.doAfterTextChanged
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseConstraintCustomView
import com.example.utt.hnccomputer.databinding.ViewEditInformationBinding
import com.example.utt.hnccomputer.extension.gone
import com.example.utt.hnccomputer.extension.visible


class EditInformationView(context: Context, attrs: AttributeSet? = null) :
    BaseConstraintCustomView<ViewEditInformationBinding>(context, attrs) {

    companion object {
        const val TYPE_PASSWORD = 1
        const val TYPE_NUMBER = 2
    }

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
        when (a.getInt(R.styleable.EditInformationView_edit_type, 0)) {
            TYPE_PASSWORD -> binding.edtInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            TYPE_NUMBER -> binding.edtInput.inputType = InputType.TYPE_CLASS_NUMBER
            else -> {}
        }
    }

    override val styleAble: IntArray?
        get() = R.styleable.EditInformationView

    fun setInformation(information: String?) {
        binding.edtInput.setText(information)
    }

    fun setPasswordType(boolean: Boolean) {
        binding.edtInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    fun getInformation() = binding.edtInput.text.toString().trim()
}