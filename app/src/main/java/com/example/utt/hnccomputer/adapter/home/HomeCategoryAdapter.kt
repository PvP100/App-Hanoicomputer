package com.example.utt.hnccomputer.adapter.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.entity.model.HomeCategory
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.extension.gone
import com.example.utt.hnccomputer.extension.inflate
import kotlinx.android.synthetic.main.include_category_header_home.view.*
import kotlinx.android.synthetic.main.item_cell_home_category.view.*

class HomeCategoryAdapter(context: Context) : RecyclerViewAdapter(context, false) {

    var onProductClick: (String?) -> Unit = {}

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return HomeCategoryViewHolder(parent.inflate(R.layout.item_cell_home_category))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val homeCategory = getItem(position, HomeCategory::class.java)
        (holder as HomeCategoryViewHolder).itemView.apply {
            homeCategory?.product?.let {
                if (it.isNotEmpty()) {
                    val productAdapter = HomeProductAdapter(this.context)
                    rcv_home_product.adapter = productAdapter
                    productAdapter.addModels(it, false)
                    header_home_category.tv_category.text = homeCategory.categoryTitle
                    productAdapter.addOnItemClickListener(object : OnItemClickListener {
                        override fun onItemClick(
                            adapter: RecyclerView.Adapter<*>,
                            viewHolder: RecyclerView.ViewHolder?,
                            viewType: Int,
                            position: Int
                        ) {
                            onProductClick(productAdapter.getItem(position, Product::class.java)?.id)
                        }
                    })
                } else {
                    gone()
                }
            }
        }
    }

    inner class HomeCategoryViewHolder(view: View) : NormalViewHolder(view)

}