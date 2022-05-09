package com.example.utt.hnccomputer.ui.fragment.category_detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.databinding.FragmentCategoryDetailBinding
import com.example.utt.hnccomputer.entity.model.Category
import com.example.utt.hnccomputer.utils.BundleKey
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryDetailFragment : BaseFragment<FragmentCategoryDetailBinding>() {

    private val categoryDetailViewModel: CategoryDetailViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentCategoryDetailBinding
    ) {
        arguments?.let {
            if (it.containsKey(BundleKey.KEY_DETAIL_CATEGORY)) {
                categoryDetailViewModel.categoryId = (it.getSerializable(BundleKey.KEY_DETAIL_CATEGORY) as Category).id
                binding.header.setHeaderTitle((it.getSerializable(BundleKey.KEY_DETAIL_CATEGORY) as Category).title)
            }
        }
    }

    override fun initData() {
        with(categoryDetailViewModel) {
            getProduct()
            product.observe(this@CategoryDetailFragment) {
                handleObjectLoadMoreResponse(it, binding.progressBar)
            }
        }
    }

    override fun <U> getListLoadMoreResponse(data: U?, isRefresh: Boolean, canLoadMore: Boolean) {
        super.getListLoadMoreResponse(data, isRefresh, canLoadMore)
        Log.v("phongpv", "234234")
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoryDetailBinding {
        return FragmentCategoryDetailBinding.inflate(inflater, container, false)
    }
}