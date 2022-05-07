package com.example.utt.hnccomputer.base.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter


class DiffUtilCallBack(
    private val oldItems: List<RecyclerViewAdapter.ModelWrapper>,
    private val newItems: List<RecyclerViewAdapter.ModelWrapper>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].id == newItems.get(newItemPosition).id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}
