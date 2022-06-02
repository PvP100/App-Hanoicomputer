package com.example.utt.hnccomputer.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseCustomDialog
import com.example.utt.hnccomputer.databinding.DialogChangePasswordBinding
import com.example.utt.hnccomputer.databinding.DialogChangeReceiverCustomerBinding
import com.example.utt.hnccomputer.extension.toast

class ChangeReceiverCustomerDialog : BaseCustomDialog<DialogChangeReceiverCustomerBinding>() {

    interface OnChangeCustomerListener {
        fun onChangeCustomer(name: String, phoneNumber: String, address: String)
    }

    private var listener: OnChangeCustomerListener? = null

    fun setOnChangePasswordListener(listener: OnChangeCustomerListener) {
        this.listener = listener
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_white_radius_12dp)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: DialogChangeReceiverCustomerBinding
    ) {
        initListener()
    }

    private fun initListener() {
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnChangePassword.setOnClickListener {
                if (oldPassword.getInformation().isNotEmpty() && newPassword.getInformation().isNotEmpty() && rePassword.getInformation().isNotEmpty()) {
                    listener?.onChangeCustomer(oldPassword.getInformation(), newPassword.getInformation(), rePassword.getInformation())
                    this@ChangeReceiverCustomerDialog.dismiss()
                } else {
                    toast("Vui lòng nhập đầy đủ thông tin")
                }
            }
        }
    }

    override fun getDialogBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogChangeReceiverCustomerBinding {
        return DialogChangeReceiverCustomerBinding.inflate(inflater, container, false)
    }
}