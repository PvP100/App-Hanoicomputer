package com.example.utt.hnccomputer.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseCustomDialog
import com.example.utt.hnccomputer.databinding.DialogChangePasswordBinding
import com.example.utt.hnccomputer.extension.toast

class ChangePasswordDialog : BaseCustomDialog<DialogChangePasswordBinding>() {

    interface OnChangePassword {
        fun onChangePassword(oldPassword: String, newPassword: String)
    }

    private var listener: OnChangePassword? = null

    fun setOnChangePasswordListener(listener: OnChangePassword) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
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
                if (newPassword.getPassword() == rePassword.getPassword()) {
                    listener?.onChangePassword(oldPassword.getPassword(), newPassword.getPassword())
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