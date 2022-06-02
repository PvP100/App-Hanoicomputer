package com.example.utt.hnccomputer.adapter.home

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.extension.*
import kotlinx.android.synthetic.main.item_cell_product_category_detail.view.*

class CategoryProductAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {

    var addToCart: (Product) -> Unit = {}

    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(parent.inflate(R.layout.layout_load_more))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {}

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return HomeCategoryViewHolder(parent.inflate(R.layout.item_cell_product_category_detail))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val product = getItem(position, Product::class.java)
        (holder as HomeCategoryViewHolder).itemView.apply {
            btn_add_to_cart.setOnClickListener {
                product?.let { it1 -> addToCart(it1) }
            }
            if (product?.isSale == 1) {
                tv_home_product_price.setTextColor(Color.parseColor("#A8A8A8"))
                tv_sale_percent.text = "${product.salePercent}%"
                tv_product_sale_price.text = product.salePrice.convertToVnd()
                tv_home_product_price.textSize = 12f
                divider.visible()
                tv_sale_percent.visible()
                tv_product_sale_price.visible()
            } else {
                tv_home_product_price.setTextColor(Color.parseColor("#000000"))
                tv_home_product_price.textSize = 14f
                divider.gone()
                tv_product_sale_price.gone()
                tv_sale_percent.gone()
            }
            img_product.loadImage(product?.logoUrl)
            tv_home_product_name.text = product?.name
            tv_home_product_price.text = product?.price?.convertToVnd()
        }
    }

    inner class HomeCategoryViewHolder(view: View) : RecyclerViewAdapter.NormalViewHolder(view)

}