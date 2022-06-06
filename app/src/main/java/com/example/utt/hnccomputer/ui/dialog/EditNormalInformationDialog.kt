package com.example.utt.hnccomputer.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseCustomDialog
import com.example.utt.hnccomputer.databinding.DialogEditNormalInformationBinding

class EditNormalInformationDialog : BaseCustomDialog<DialogEditNormalInformationBinding>() {

    private var headerTitle: String? = null

    private var information: String? = null

    private var isNumber: Boolean = false

    private var listener: OnUpdateListener? = null

    interface OnUpdateListener {
        fun onUpdate(text: String)
    }

    fun setListener(listener: OnUpdateListener) {
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
        binding: DialogEditNormalInformationBinding
    ) {
        binding.tvHeader.text = headerTitle
        binding.editInformation.setInformation(information)
        if (isNumber) {
            binding.editInformation.setNumberType()
        }
        initListener()
    }

    fun setDetail(headerTitle: String, information: String, isNumber: Boolean = false) {
        this.headerTitle = headerTitle
        this.information = information
        this.isNumber = isNumber
    }

    private fun initListener() {
        binding.apply {
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnChangePassword.setOnClickListener {
                if (!information.equals(editInformation.getInformation()) && editInformation.getInformation().isNotEmpty()) {
                    listener?.onUpdate(editInformation.getInformation())
                } else {
                    dismiss()
                }
            }
        }
    }

    override fun getDialogBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogEditNormalInformationBinding {
        return DialogEditNormalInformationBinding.inflate(inflater, container, false)
    }
}