package com.example.utt.hnccomputer.adapter.filter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.databinding.ItemCellProductCategoryFilterBinding
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.entity.model.Category

class FilterProductOptionAdapter(context: Context?) :
    RecyclerViewAdapter(context, false) {

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return FilterProductFilterViewHolder(
            ItemCellProductCategoryFilterBinding.inflate(
            LayoutInflater.from(context), parent, false))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val data = getItem(position, Any::class.java)
        (holder as FilterProductFilterViewHolder).binding.apply {
            if (data is Category) {
                tvTitle.text = data.title
                isSelected = data.isSelected
            }
            if (data is Brand) {
                tvTitle.text = data.brandName
                isSelected = data.isSelected
            }
        }
    }

    inner class FilterProductFilterViewHolder(val binding: ItemCellProductCategoryFilterBinding) : NormalViewHolder(binding.root)
}