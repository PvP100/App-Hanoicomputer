package com.example.utt.hnccomputer.ui.fragment.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.adapter.home.HomeBrandAdapter
import com.example.utt.hnccomputer.adapter.home.HomeCategoryAdapter
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.HomeFragmentBinding
import com.example.utt.hnccomputer.entity.model.Banner
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.entity.model.HomeCategory
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.extension.onAvoidDoubleClick
import com.example.utt.hnccomputer.ui.fragment.brand.BrandFragment
import com.example.utt.hnccomputer.ui.fragment.cart.CartFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding>() {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var homeBrandAdapter: HomeBrandAdapter

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

        initData()
        initListener()
    }

    override fun initData() {
        with(viewModel) {
            getBanner()
            getHomeBrand()
            getHomeCategory()
            banner.observe(this@HomeFragment) {
                handleListResponse(it, binding.progressBar)
            }
            brand.observe(this@HomeFragment) {
                handleObjectLoadMoreResponse(it, binding.progressBar)
            }
            homeCategory.observe(this@HomeFragment) {
                handleListResponse(it, binding.progressBar)
            }
        }
    }

    override fun <U> getListResponse(data: List<U>?) {
        super.getListResponse(data)
        if (data?.first() is HomeCategory) {
            homeCategoryAdapter.refresh(data as List<HomeCategory>)
        } else if (data?.first() is Banner) {
            binding.banner.setBanner(data as List<Banner>)
        }
    }

    override fun <U> getListLoadMoreResponse(data: U?, isRefresh: Boolean, canLoadMore: Boolean) {
        super.getListLoadMoreResponse(data, isRefresh, canLoadMore)
        if (data is ResultResponse<*>) {
            if (data.results.first() is Brand) {
                homeBrandAdapter.refresh(data.results as List<Brand>)
            }
        }
    }

    override fun initListener() {
        binding.apply {
            brandCategory.tvAll.onAvoidDoubleClick {
                transitFragment(
                    BrandFragment(),
                    R.id.parent_container
                )
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