package com.example.utt.hnccomputer.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.adapter.filter.FilterProductOptionAdapter
import com.example.utt.hnccomputer.base.BaseCustomDialog
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.databinding.DialogFilterProductBinding
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.entity.model.Category
import com.example.utt.hnccomputer.extension.onAvoidDoubleClick


class FilterProductDialog : BaseCustomDialog<DialogFilterProductBinding>() {

    private val categoryAdapter: FilterProductOptionAdapter by lazy {
        FilterProductOptionAdapter(requireContext())
    }

    private val brandAdapter: FilterProductOptionAdapter by lazy {
        FilterProductOptionAdapter(requireContext())
    }

    var onReturnFilter: (categoryId: Int?, brandId: Int?) -> Unit = { _, _ -> }

    private var oldBrandPosition: Int? = null

    private var oldCategoryPostion: Int? = null

    private var categoryId: Int? = null

    private var brandId: Int? = null

    private var listCategory: List<Category>? = null

    private var listBrand: List<Brand>? = null

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: DialogFilterProductBinding
    ) {
        oldBrandPosition = null
        oldCategoryPostion = null
        categoryId = null
        brandId = null
        initRcv()
        initListener()
    }

    private fun initRcv() {
        binding.rcvCategory.adapter = categoryAdapter
        binding.rcvBrand.adapter = brandAdapter
        listCategory?.let { categoryAdapter.refresh(it) }
        listBrand?.let { brandAdapter.refresh(it) }
    }

    fun setListCategory(listCategory: List<Category>?) {
        this.listCategory = listCategory
    }

    fun setListBrand(listBrand: List<Brand>?) {
        this.listBrand = listBrand
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    private fun initListener() {
        binding.apply {
            btnFilter.onAvoidDoubleClick {
                onReturnFilter(categoryId, brandId)
                dismiss()
            }
            cancelButton.setOnClickListener {
                dismiss()
            }
            categoryAdapter.addOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(
                    adapter: RecyclerView.Adapter<*>,
                    viewHolder: RecyclerView.ViewHolder?,
                    viewType: Int,
                    position: Int
                ) {
                    if (position == oldCategoryPostion) {
                        return
                    }

                    val item = categoryAdapter.getItem(position, Category::class.java)

                    item?.let {
                        it.isSelected = !it.isSelected
                    }

                    val oldItem = oldCategoryPostion?.let { categoryAdapter.getItem(it, Category::class.java) }

                    oldItem?.let {
                        it.isSelected = !it.isSelected
                    }

                    categoryAdapter.notifyItemChanged(position)
                    oldCategoryPostion?.let { categoryAdapter.notifyItemChanged(it) }

                    oldCategoryPostion = position
                    categoryId = item?.id
                }

            })
            brandAdapter.addOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(
                    adapter: RecyclerView.Adapter<*>,
                    viewHolder: RecyclerView.ViewHolder?,
                    viewType: Int,
                    position: Int
                ) {
                    if (position == oldBrandPosition) {
                        return
                    }

                    val item = brandAdapter.getItem(position, Brand::class.java)

                    item?.let {
                        it.isSelected = !it.isSelected
                    }

                    val oldItem = oldBrandPosition?.let { brandAdapter.getItem(it, Brand::class.java) }

                    oldItem?.let {
                        it.isSelected = !it.isSelected
                    }

                    brandAdapter.notifyItemChanged(position)
                    oldBrandPosition?.let { categoryAdapter.notifyItemChanged(it) }

                    oldBrandPosition = position
                    brandId = item?.id
                }

            })
        }
    }

    override fun getDialogBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DialogFilterProductBinding {
        return DialogFilterProductBinding.inflate(inflater, container, false)
    }
}