package com.example.utt.hnccomputer.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.adapter.home.HomeBrandAdapter
import com.example.utt.hnccomputer.adapter.home.HomeCategoryAdapter
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.base.entity.BaseError
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.customview.HncSearchView
import com.example.utt.hnccomputer.databinding.HomeFragmentBinding
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.extension.onAvoidDoubleClick
import com.example.utt.hnccomputer.extension.toast
import com.example.utt.hnccomputer.ui.fragment.brand.BrandFragment
import com.example.utt.hnccomputer.ui.fragment.cart.CartFragment
import com.example.utt.hnccomputer.ui.fragment.category_detail.CategoryDetailFragment
import com.example.utt.hnccomputer.ui.fragment.product_detail.ProductDetailFragment
import com.example.utt.hnccomputer.ui.fragment.search.SearchFragment
import com.example.utt.hnccomputer.utils.BundleKey
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var homeBrandAdapter: HomeBrandAdapter

    private var isAdd = false

    private lateinit var homeCategoryAdapter: HomeCategoryAdapter

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: HomeFragmentBinding
    ) {

        homeBrandAdapter = HomeBrandAdapter(requireContext())
        homeCategoryAdapter = HomeCategoryAdapter(requireContext())

        binding.rcvBrand.adapter = homeBrandAdapter
        binding.rcvHomeCategory.adapter = homeCategoryAdapter
    }

    override fun handleValidateError(throwable: BaseError?) {
        throwable?.error?.let {
            toast(it)
        }
    }

    override fun initData() {
        with(viewModel) {
            getHomeBrand()
            banner.observe(this@HomeFragment) {
                handleListResponse(it, binding.progressBar)
            }
            response.observe(this@HomeFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    if (!isAdd) {
                        brand.value?.data?.results?.let { it1 ->
                            homeBrandAdapter.refresh(
                                it1
                            )
                        }
                        homeCategory.value?.data?.let { it1 -> homeCategoryAdapter.refresh(it1) }
                        banner.value?.data?.let { it1 -> binding.banner.setBanner(it1) }
                    } else {
                        toast("Thêm thành công")
                    }
                    isAdd = false
                }
            }
            homeCategory.observe(this@HomeFragment) {
                handleListResponse(it, binding.progressBar)
            }
        }
    }

    override fun initListener() {
        homeCategoryAdapter.onAddToCart = {
            isAdd = true
            viewModel.addToCart(it)
        }
        homeBrandAdapter.addOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(
                adapter: RecyclerView.Adapter<*>,
                viewHolder: RecyclerView.ViewHolder?,
                viewType: Int,
                position: Int
            ) {
                transitFragment(CategoryDetailFragment(), R.id.parent_container, Bundle().apply {
                    putSerializable(BundleKey.KEY_DETAIL_CATEGORY, homeBrandAdapter.getItem(position, Brand::class.java))
                })
            }

        })
        homeCategoryAdapter.onProductClick = {
            transitFragment(
                ProductDetailFragment(),
                R.id.parent_container,
                Bundle().apply {
                    putString(BundleKey.KEY_PRODUCT_DETAIL, it)
                }
            )
        }
        binding.apply {
            brandCategory.tvAll.onAvoidDoubleClick {
                transitFragment(
                    BrandFragment(),
                    R.id.parent_container
                )
            }
            header.getSearchView().listener = object : HncSearchView.IOnSearchListener {
                override fun onSearch(value: String) {
                    transitFragment(
                        SearchFragment(),
                        R.id.parent_container,
                        Bundle().apply {
                            putString(BundleKey.KEY_SEARCH_VALUE, value)
                        }
                    )
                }
            }
            header.listener = object : HncHeaderView.IOnClickHeader {
                override fun onLeftClick() {
                }

                override fun onRightClick() {
                   transitFragment(CartFragment(), R.id.parent_container)
                }
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): HomeFragmentBinding {
        return HomeFragmentBinding.inflate(inflater, container, false)
    }
}