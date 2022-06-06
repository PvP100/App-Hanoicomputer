package com.example.utt.hnccomputer.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseCustomDialog
import com.example.utt.hnccomputer.databinding.DialogChangePasswordBinding
import com.example.utt.hnccomputer.extension.isValidPassword
import com.example.utt.hnccomputer.extension.toast

class ChangePasswordDialog : BaseCustomDialog<DialogChangePasswordBinding>() {

    interface OnChangePassword {
        fun onChangePassword(oldPassword: String, newPassword: String)
    }

    private var listener: OnChangePassword? = null

    fun setOnChangePasswordListener(listener: OnChangePassword) {
        this.listener = listener
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_white_radius_12dp)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: DialogChangePasswordBinding
    ) {
        initListener()
    }

    private fun initListener() {
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnChangePassword.setOnClickListener {
                if (!newPassword.getInformation().isValidPassword()) {
                    toast("Mật khẩu mới cần có chữ hoa thường và số")
                    return@setOnClickListener
                }
                if (newPassword.getInformation() == rePassword.getInformation()) {
                    listener?.onChangePassword(oldPassword.getInformation(), newPassword.getInformation())
                } else {
                    toast("Mật khẩu nhập lại không khớp")
                }
            }
        }
    }

    override fun getDialogBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogChangePasswordBinding {
        return DialogChangePasswordBinding.inflate(inflater, container, false)
    }
}