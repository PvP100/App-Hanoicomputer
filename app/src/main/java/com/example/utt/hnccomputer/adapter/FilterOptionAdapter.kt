package com.example.utt.hnccomputer.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.entity.model.FilterOption
import com.example.utt.hnccomputer.extension.inflate
import com.example.utt.hnccomputer.extension.*
import kotlinx.android.synthetic.main.item_cell_selected.view.*

class FilterOptionAdapter(context: Context?, enableSelectedMode: Boolean) :
    RecyclerViewAdapter(context, enableSelectedMode) {

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return FilterOptionViewHoler(parent.inflate(R.layout.item_cell_selected))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val filterOptionViewHolder = holder as FilterOptionViewHoler
        val filterOption = getItem(position, FilterOption::class.java)

        filterOptionViewHolder.itemView.apply {
            tv_filter_title.text = filterOption?.title
            if (filterOption?.isChecked == true) {
                ic_check_mark.visible()
            } else {
                ic_check_mark.gone()
            }
        }
    }

    inner class FilterOptionViewHoler(itemView: View) : NormalViewHolder(itemView) {
        init {

        }
    }
}