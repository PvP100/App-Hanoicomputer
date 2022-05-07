package com.example.utt.hnccomputer.base.adapter.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.extension.inflate
import com.example.utt.hnccomputer.extension.loadImage
import kotlinx.android.synthetic.main.item_cell_brand_home.view.*

class HomeBrandAdapter(context: Context) : RecyclerViewAdapter(context, false) {
    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return BrandViewHolder(parent.inflate(R.layout.item_cell_brand_home))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val brand = getItem(position, Brand::class.java)
        (holder as BrandViewHolder).itemView.apply {
            ic_logo.loadImage(brand?.imgUrl)
        }


    }

    inner class BrandViewHolder(view: View) : NormalViewHolder(view)

}