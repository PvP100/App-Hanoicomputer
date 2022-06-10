package com.example.utt.hnccomputer.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseCustomDialog
import com.example.utt.hnccomputer.databinding.DialogConfirmOrderBinding

class ConfirmOrderDialog : BaseCustomDialog<DialogConfirmOrderBinding>() {

    var onDeleteListener: () -> Unit = {}

    var content: String? = null

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_white_radius_12dp)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: DialogConfirmOrderBinding
    ) {
        binding.content.text = content
        initListener()
    }

    fun setTitle(content: String) {
        this.content = content
    }

    private fun initListener() {
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnDelete.setOnClickListener {
                onDeleteListener()
                dismiss()
            }
        }
    }

    override fun getDialogBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogConfirmOrderBinding {
        return DialogConfirmOrderBinding.inflate(inflater, container, false)
    }
}