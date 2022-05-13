package com.example.utt.hnccomputer.customview

import android.content.Context
import android.content.res.TypedArray
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseConstraintCustomView
import com.example.utt.hnccomputer.databinding.ViewRegisterInputBinding
import com.example.utt.hnccomputer.entity.model.Gender
import com.example.utt.hnccomputer.extension.*

class RegisterInputView(context: Context, attrs: AttributeSet? = null) :
    BaseConstraintCustomView<ViewRegisterInputBinding>(context, attrs) {

    companion object {
        const val GENDER = 1
        const val PHONE_NUMBER = 2
        const val PASSWORD = 3
        const val EMAIL = 4
        const val BIRTHDAY = 5
        const val RE_PASSWORD = 6
        const val ADDRESS = 7
    }

    private lateinit var spinnerAdapter: ArrayAdapter<Gender>

    private var listener: OnEdtClick? = null

    interface OnEdtClick {
        fun onClick()
    }

    fun setOnEdtClick(onClick: OnEdtClick) {
        this.listener = onClick
    }

    override fun getCustomViewBinding(context: Context): ViewRegisterInputBinding {
        return ViewRegisterInputBinding.inflate(LayoutInflater.from(context), this, false)
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun initStyAble(a: TypedArray) {
        binding.apply {
            when (a.getInt(R.styleable.RegisterInputView_input_type, 0)) {
                GENDER -> {
                    spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, Gender.values())
                    spinnerGender.visible()
                    icDropdown.visible()
                    edtInput.invisible()
                    spinnerGender.adapter = spinnerAdapter
                    spinnerGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            p0: AdapterView<*>?,
                            p1: View?,
                            p2: Int,
                            p3: Long
                        ) {
                            val result = p0?.getItemAtPosition(p2) as Gender
                            binding.edtInput.setText(result.type.toString())
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {

                        }

                    }
                }
                BIRTHDAY -> {
                    edtInput.hint = "Chọn ngày sinh"
                    edtInput.isFocusable = false
                    edtInput.isCursorVisible = false
                    edtInput.onAvoidDoubleClick {
                        context.showDatePickerDialog {
                            edtInput.setText(it)
                        }
                    }
                }
                EMAIL -> {
                    edtInput.hint = "example@gmail.com"
                }
                PHONE_NUMBER -> {
                    edtInput.hint = "0123456789"
                    edtInput.inputType = InputType.TYPE_CLASS_PHONE
                }
                PASSWORD -> {
                    edtInput.hint = "Nhập mật khẩu"
                    edtInput.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                }
                ADDRESS -> {
                    edtInput.hint = "Nhập địa chỉ nhà"
                }
                RE_PASSWORD -> {
                    edtInput.hint = "Nhập lại mật khẩu"
                    edtInput.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                } else -> {
                edtInput.hint = "Nhập họ tên"
            }
            }
            if (a.hasValue(R.styleable.RegisterInputView_header_title_input)) {
                tvTitle.text = a.getString(R.styleable.RegisterInputView_header_title_input)
            }
        }

    }

    fun setInformation(information: String) {
        binding.edtInput.setText(information)
    }

    fun getText() = binding.edtInput.text.toString().trim()

    override val styleAble: IntArray?
        get() = R.styleable.RegisterInputView
}