package com.example.utt.hnccomputer.base.adapter.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.extension.inflate
import com.example.utt.hnccomputer.extension.loadImage
import kotlinx.android.synthetic.main.item_cell_home_product.view.*

class HomeProductAdapter(context: Context) : RecyclerViewAdapter(context, false) {

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return HomeCategoryViewHolder(parent.inflate(R.layout.item_cell_home_product))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val product = getItem(position, Product::class.java)
        (holder as HomeCategoryViewHolder).itemView.apply {
            img_product.loadImage(product?.logoUrl)
            tv_home_product_name.text = product?.name
            tv_home_product_price.text = product?.price.toString()
        }
    }

    inner class HomeCategoryViewHolder(view: View) : RecyclerViewAdapter.NormalViewHolder(view)

}