package com.example.utt.hnccomputer.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.adapter.EndlessLoadingRecyclerViewAdapter
import com.example.utt.hnccomputer.databinding.ItemSearchBinding
import com.example.utt.hnccomputer.entity.User
import com.example.utt.hnccomputer.extension.inflate
import kotlinx.android.synthetic.main.item_search.view.*

class SearchAdapter(context: Context) : EndlessLoadingRecyclerViewAdapter(context, false) {
    override fun initLoadingViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return LoadingViewHolder(
            parent.inflate(R.layout.item_search)
        )
    }

    override fun bindLoadingViewHolder(loadingViewHolder: LoadingViewHolder, position: Int) {
    }

    override fun initNormalViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(
            binding.root
        )
    }

    override fun bindNormalViewHolder(holder: NormalViewHolder, position: Int) {
        val searchViewHolder = holder as SearchViewHolder
        val search = getItem(position, User::class.java)!!
        searchViewHolder.itemView.tv_id.text = search.id.toString()
        searchViewHolder.itemView.tv_title.text = search.name
    }

    class SearchViewHolder(view: View) : NormalViewHolder(view)
}