package com.example.utt.hnccomputer.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseCustomDialog
import com.example.utt.hnccomputer.databinding.DialogLoginBinding

class LoginDialog : BaseCustomDialog<DialogLoginBinding>() {

    interface OnDialogListener {
        fun onLogin()
    }

    private var listener: OnDialogListener? = null

    fun setOnDialogListener(listener: OnDialogListener) {
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
        binding: DialogLoginBinding
    ) {
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnLogin.setOnClickListener {
                listener?.onLogin()
            }
        }
    }

    override fun getDialogBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogLoginBinding {
        return DialogLoginBinding.inflate(inflater, container, false)
    }


}