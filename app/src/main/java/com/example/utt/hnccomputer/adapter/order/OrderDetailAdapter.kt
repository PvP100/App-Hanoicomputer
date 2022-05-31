package com.example.utt.hnccomputer.adapter.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.databinding.ItemCellOrderDetailBinding
import com.example.utt.hnccomputer.entity.model.ProductOrder
import com.example.utt.hnccomputer.extension.convertToVnd
import com.example.utt.hnccomputer.extension.loadImage

class OrderDetailAdapter(context: Context?, enableSelectedMode: Boolean = false) :
    RecyclerViewAdapter(context, enableSelectedMode) {
    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return OrderDetailViewHolder(ItemCellOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as OrderDetailViewHolder).bind(getItem(position, ProductOrder::class.java))
    }

    inner class OrderDetailViewHolder(private val binding: ItemCellOrderDetailBinding) : NormalViewHolder(binding.root) {
        fun bind(product: ProductOrder?) {
            binding.apply {
                imgAva.loadImage(product?.productUrl)
                tvMoney.text = product?.price?.convertToVnd()
                model = product
            }
        }
    }
}