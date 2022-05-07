package com.example.utt.hnccomputer.base.adapter.banner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.entity.model.Banner
import com.example.utt.hnccomputer.extension.inflate
import com.example.utt.hnccomputer.extension.loadImage
import kotlinx.android.synthetic.main.item_banner_hnc.view.*

class BannerPagerAdapter(context: Context) : RecyclerViewAdapter(context, false) {

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return BannerViewHolder(parent.inflate(R.layout.item_banner_hnc))
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val item = getItem(position, Banner::class.java)
        (holder as BannerViewHolder).itemView.apply {
            banner_img.loadImage(item?.imgUrl)
        }
    }

    inner class BannerViewHolder(view: View) : NormalViewHolder(view)
}