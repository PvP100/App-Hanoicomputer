package com.example.utt.hnccomputer.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseCustomDialog
import com.example.utt.hnccomputer.databinding.DialogPickUpAvatarBinding
import com.example.utt.hnccomputer.extension.onAvoidDoubleClick

class PickUpAvatarDialog : BaseCustomDialog<DialogPickUpAvatarBinding>() {

    interface OnSelectImageListener {
        fun onTakePhoto()
        fun onChooseFromGallery()
    }

    private var listener: OnSelectImageListener? = null

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: DialogPickUpAvatarBinding
    ) {
        binding.apply {
            btnClose.setOnClickListener {
                dismiss()
            }
            btnChooseFromGallery.onAvoidDoubleClick {
                listener?.onChooseFromGallery()
            }
            btnTakePhoto.onAvoidDoubleClick {
                listener?.onTakePhoto()
            }
        }
    }

    fun setListener(listener: OnSelectImageListener) {
        this.listener = listener
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.9).toInt()
        dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_white_radius_12dp)
    }

    override fun getDialogBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogPickUpAvatarBinding {
        return DialogPickUpAvatarBinding.inflate(inflater, container, false)
    }
}