package com.example.utt.hnccomputer.adapter.category

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.entity.model.Category
import com.example.utt.hnccomputer.extension.inflate
import com.example.utt.hnccomputer.extension.loadImage
import kotlinx.android.synthetic.main.item_cell_category_page.view.*

class CategoryAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(parent.inflate(R.layout.layout_load_more))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return CategoryViewHolder(parent.inflate(R.layout.item_cell_category_page))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val item = getItem(position, Category::class.java)
        (holder as CategoryViewHolder).itemView.apply {
            img_category.loadImage(item?.imgUrl)
            tv_category_title.text = item?.title
        }
    }

    inner class CategoryViewHolder(view: View) : NormalViewHolder(view)
}