package com.example.utt.hnccomputer.ui.fragment.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.BaseViewStubFragment
import com.example.utt.hnccomputer.base.adapter.category.CategoryAdapter
import com.example.utt.hnccomputer.databinding.FragmentCategoryBinding
import com.example.utt.hnccomputer.entity.model.Category
import com.example.utt.hnccomputer.entity.response.ResultResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseViewStubFragment<FragmentCategoryBinding>() {

    private val viewModel: CategoryViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter

    override fun initListener() {
        binding.apply {
            rcvCategory.setOnRefreshListener {
                viewModel.getCategory()
            }
        }
    }

    override fun initData() {

    }

    override fun <U> getListLoadMoreResponse(data: U?, isRefresh: Boolean, canLoadMore: Boolean) {
        super.getListLoadMoreResponse(data, isRefresh, canLoadMore)
        if (data is ResultResponse<*>) {
            binding.rcvCategory.refresh(data = data.results as List<Category>)
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoryBinding {
        return FragmentCategoryBinding.inflate(inflater, container, false)
    }

    override fun onCreateViewAfterViewStubInflated(
        inflatedView: View,
        savedInstanceState: Bundle?
    ) {
        categoryAdapter = CategoryAdapter(requireContext())
        binding.rcvCategory.setAdapter(categoryAdapter)
        binding.rcvCategory.setListLayoutManager(LinearLayout.VERTICAL)
        initListener()
        with(viewModel) {
            getCategory()
            category.observe(this@CategoryFragment) {
                handleObjectLoadMoreResponse(it, binding.progressBar)
            }
        }
    }

    override fun getViewStubLayoutResource(): Int {
        return R.layout.fragment_category
    }
}