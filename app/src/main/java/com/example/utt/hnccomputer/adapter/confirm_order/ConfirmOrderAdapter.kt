package com.example.utt.hnccomputer.adapter.confirm_order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.databinding.ItemCellConfirmOrderBinding
import com.example.utt.hnccomputer.extension.convertToVnd
import com.example.utt.hnccomputer.extension.loadImage

class ConfirmOrderAdapter(context: Context?, enableSelectedMode: Boolean = false) :
    RecyclerViewAdapter(context, enableSelectedMode) {
    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return ConfirmOrderViewHolder(ItemCellConfirmOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val confirmOrder = getItem(position, MyOrderInformation::class.java)
        (holder as ConfirmOrderViewHolder).binding.apply {
            model = confirmOrder
            imgAva.loadImage(confirmOrder?.imgUrl)
            tvMoney.text = confirmOrder?.price?.convertToVnd()
        }
    }

    inner class ConfirmOrderViewHolder(val binding: ItemCellConfirmOrderBinding) : NormalViewHolder(binding.root)
}