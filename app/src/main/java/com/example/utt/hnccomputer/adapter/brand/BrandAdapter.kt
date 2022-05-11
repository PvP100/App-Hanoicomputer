package com.example.utt.hnccomputer.adapter.brand

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.example.utt.hnccomputer.databinding.ItemCellBrandBinding
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.extension.inflate
import com.example.utt.hnccomputer.extension.loadImage

class BrandAdapter(context: Context, enableSelectedMode: Boolean = false) :
    EndlessLoadingRecyclerViewAdapter(context, enableSelectedMode) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(parent.inflate(R.layout.layout_load_more))
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {}

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return BrandViewHolder(ItemCellBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as BrandViewHolder).bind(getItem(position, Brand::class.java), position)
    }

    inner class BrandViewHolder(private val binding: ItemCellBrandBinding) : NormalViewHolder(binding.root) {

        fun bind(model: Brand?, position: Int) {
            binding.apply {
                model?.let {
                    imgBrand.loadImage(it.imgUrl)
                }
            }
        }

    }
}