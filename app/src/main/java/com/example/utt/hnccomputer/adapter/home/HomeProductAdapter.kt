package com.example.utt.hnccomputer.adapter.home

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.databinding.ItemCellHomeProductBinding
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.extension.*

class HomeProductAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {

    var addToCart: (Product) -> Unit = {}

    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(parent.inflate(R.layout.layout_load_more))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {}

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return HomeCategoryViewHolder(ItemCellHomeProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val product = getItem(position, Product::class.java)
        product?.let { (holder as HomeCategoryViewHolder).bind(it) }
    }

    inner class HomeCategoryViewHolder(private val binding: ItemCellHomeProductBinding) : RecyclerViewAdapter.NormalViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                btnAddToCart.onAvoidDoubleClick {
                    addToCart(product)
                }
                if (product.isSale == 1) {
                    tvSalePrice.setTextColor(Color.parseColor("#A8A8A8"))
                    tvSalePercent.text = "${product.salePercent}%"
                    tvSalePrice.textSize = 12f
                    dividerSale.visible()
                    tvHomeProductPrice.visible()
                } else {
                    dividerSale.gone()
                    tvHomeProductPrice.gone()
                    tvSalePercent.gone()
                }
                imgProduct.loadImage(product.logoUrl)
                tvSalePrice.text = product.price.convertToVnd()
                tvHomeProductPrice.text = product.salePrice.convertToVnd()
                model = product
            }
        }
    }

}