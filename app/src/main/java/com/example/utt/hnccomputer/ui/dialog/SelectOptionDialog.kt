package com.example.utt.hnccomputer.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.adapter.FilterOptionAdapter
import com.example.utt.hnccomputer.base.BaseCustomDialog
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.databinding.DialogSelectOptionBinding
import com.example.utt.hnccomputer.entity.model.FilterOption

class SelectOptionDialog : BaseCustomDialog<DialogSelectOptionBinding>() {

    private lateinit var filterAdapter: FilterOptionAdapter

    private var selectedPosition: Int = 0

    private var filter: List<FilterOption>? = null

    var onReturnFilterId: (filterId: Int?, filterTitle: String?) -> Unit = {_, _ ->}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: DialogSelectOptionBinding
    ) {
        filterAdapter = FilterOptionAdapter(requireContext(), false)
        binding.rcvFilter.adapter = filterAdapter
        filter?.let {
            it.mapIndexed { index, filterOption -> if (index == selectedPosition) filterOption.isChecked = true }
            filterAdapter.refresh(it)
        }
        initListener()
    }

    fun setFilter(filter: List<FilterOption>) {
        this.filter = filter
    }

    fun setSelectedPosition(selected: Int?) {
        selected?.let {
            selectedPosition = it
        }
    }

    private fun initListener() {
        binding.apply {
            container.setOnClickListener {
                dismissAllowingStateLoss()
            }
            rcvFilter.adapter = filterAdapter

            filterAdapter.addOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(
                    adapter: RecyclerView.Adapter<*>,
                    viewHolder: RecyclerView.ViewHolder?,
                    viewType: Int,
                    position: Int
                ) {
                    if(selectedPosition == position){
                        return
                    }

                    val item = filterAdapter.getItem(position, FilterOption::class.java)
                    item?.isChecked = true

                    filterAdapter.getItem(selectedPosition, FilterOption::class.java)?.isChecked = false

                    onReturnFilterId(filterAdapter.getItem(position, FilterOption::class.java)?.filterId, filterAdapter.getItem(position, FilterOption::class.java)?.title)

                    selectedPosition = position
                    dismissAllowingStateLoss()
                }

            })

            tvCancel.setOnClickListener {
                dismissAllowingStateLoss()
            }
        }
    }

    override fun getDialogBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogSelectOptionBinding {
        return DialogSelectOptionBinding.inflate(inflater, container, false)
    }

}