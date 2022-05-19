package com.example.utt.hnccomputer.adapter.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.databinding.ItemCellCartBinding
import com.example.utt.hnccomputer.extension.convertToVnd
import com.example.utt.hnccomputer.extension.loadImage

class CartAdapter(context: Context?, enableSelectedMode: Boolean = false) :
    RecyclerViewAdapter(context, enableSelectedMode) {

    fun getQuantityChange() {

    }

    var onRemoveCart: (productId: String?, position: Int) -> Unit = { _, _ -> }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return CartViewHolder(ItemCellCartBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val model = getItem(position, MyOrderInformation::class.java)
        (holder as CartViewHolder).binding.apply {
            tvProductNameCart.text = model?.productName
            tvPrice.text = model?.price?.convertToVnd()
            imgProductCart.loadImage(model?.imgUrl)
            layoutMinusPlus.setCount(model?.quantity ?: 1)
            btnRemove.setOnClickListener {
                onRemoveCart(model?.productId, position)
            }
        }
    }

    inner class CartViewHolder(val binding: ItemCellCartBinding) : NormalViewHolder(binding.root)
}