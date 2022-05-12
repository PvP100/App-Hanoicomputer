package com.example.utt.hnccomputer.ui.fragment.category_detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.adapter.home.HomeProductAdapter
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentCategoryDetailBinding
import com.example.utt.hnccomputer.entity.model.Category
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.utils.BundleKey
import com.example.utt.hnccomputer.utils.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryDetailFragment : BaseFragment<FragmentCategoryDetailBinding>() {

    private val categoryDetailViewModel: CategoryDetailViewModel by viewModels()

    @Inject
    lateinit var productAdapter: HomeProductAdapter

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentCategoryDetailBinding
    ) {
        binding.rcvProduct.setGridLayoutManager(2)
        binding.rcvProduct.setAdapter(productAdapter)
        val spacingInPixels = resources.getDimensionPixelOffset(R.dimen.spacing_brand_rcv)
        binding.rcvProduct.getRecyclerView().addItemDecoration(SpacesItemDecoration(spacingInPixels))
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
        if (data is ResultResponse<*>) {
            binding.rcvProduct.refresh(data = data.results as List<Product>)
        }
    }

    override fun initListener() {
        binding.apply {
            header.listener = object : HncHeaderView.IOnClickHeader {
                override fun onLeftClick() {
                    activity?.onBackPressed()
                }

                override fun onRightClick() {
                    //TODO filter
                }
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCategoryDetailBinding {
        return FragmentCategoryDetailBinding.inflate(inflater, container, false)
    }
}