package com.example.utt.hnccomputer.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseCustomDialog
import com.example.utt.hnccomputer.databinding.DialogErrorLoginBinding

class ErrorResponseDialog : BaseCustomDialog<DialogErrorLoginBinding>() {

    private var errorMessage: String = "Có lỗi xảy ra!"

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_white_radius_12dp)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: DialogErrorLoginBinding
    ) {
        binding.tvErrorDialog.text = errorMessage
        binding.btnOkDialog.setOnClickListener {
            dismiss()
        }
    }

    fun setErrorMessage(error: String?) {
        error?.let {
            errorMessage = error
        }
    }

    override fun getDialogBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogErrorLoginBinding {
        return DialogErrorLoginBinding.inflate(inflater, container, false)
    }

}