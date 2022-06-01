package com.example.utt.hnccomputer.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.adapter.home.CategoryProductAdapter
import com.example.utt.hnccomputer.adapter.home.HomeProductAdapter
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentSearchBinding
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.ui.fragment.product_detail.ProductDetailFragment
import com.example.utt.hnccomputer.utils.BundleKey
import com.example.utt.hnccomputer.utils.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    @Inject
    lateinit var productAdapter: CategoryProductAdapter

    private val viewModel: SearchViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentSearchBinding
    ) {
        arguments?.let {
            if (it.containsKey(BundleKey.KEY_SEARCH_VALUE)) {
                it.getString(BundleKey.KEY_SEARCH_VALUE)?.let { it1 -> viewModel.getSearch(it1) }
            }
        }
        binding.rcvSearchProduct.adapter = productAdapter
        val spacingInPixels = resources.getDimensionPixelOffset(R.dimen.spacing_brand_rcv)
        binding.rcvSearchProduct.addItemDecoration(SpacesItemDecoration(spacingInPixels))
    }

    override fun initData() {
        with(viewModel) {
            product.observe(this@SearchFragment) {
                handleObjectLoadMoreResponse(it, binding.progressBar)
            }
        }
    }

    override fun <U> getListLoadMoreResponse(data: U?, isRefresh: Boolean, canLoadMore: Boolean) {
        super.getListLoadMoreResponse(data, isRefresh, canLoadMore)
        if (data is ResultResponse<*>) {
            if (data.results.isNotEmpty()) {
                productAdapter.refresh(data.results as List<Product>)
            }
        }
    }

    override fun initListener() {
        binding.apply {
            header.listener = object : HncHeaderView.IOnClickHeader {
                override fun onLeftClick() {
                    activity?.onBackPressed()
                }

                override fun onRightClick() {

                }

            }
        }
        productAdapter.addOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(
                adapter: RecyclerView.Adapter<*>,
                viewHolder: RecyclerView.ViewHolder?,
                viewType: Int,
                position: Int
            ) {
                transitFragment(
                    ProductDetailFragment(),
                    R.id.parent_container,
                    Bundle().apply {
                        putString(BundleKey.KEY_PRODUCT_DETAIL, productAdapter.getItem(position, Product::class.java)?.id)
                    }
                )
            }
        })
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false)
    }
}