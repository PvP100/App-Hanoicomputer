package com.example.utt.hnccomputer.adapter.banner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.extension.inflate
import com.example.utt.hnccomputer.extension.loadImage
import kotlinx.android.synthetic.main.item_cell_banner_dot.view.*

class DotAdapter(context: Context) : RecyclerViewAdapter(context, true) {

    private var currentSelect = 0

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return DotViewHolder(parent.inflate(R.layout.item_cell_banner_dot))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        (holder as DotViewHolder).itemView.apply {
            if (isItemSelected(position)) {
                dot.loadImage(R.drawable.ic_black_dot)
            } else {
                dot.loadImage(R.drawable.ic_dot)
            }
        }
    }

    fun updateTabSelected(position: Int) {
        setSelectedItem(currentSelect, false)
        setSelectedItem(position, true)
        currentSelect = position
    }

    inner class DotViewHolder(view: View) : NormalViewHolder(view)
}