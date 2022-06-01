package com.example.utt.hnccomputer.ui.fragment.brand

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.adapter.brand.BrandAdapter
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentBrandBinding
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.ui.fragment.category_detail.CategoryDetailFragment
import com.example.utt.hnccomputer.utils.BundleKey
import com.example.utt.hnccomputer.utils.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class BrandFragment : BaseFragment<FragmentBrandBinding>() {

    private val viewModel: BrandViewModel by viewModels()

    @Inject
    lateinit var brandAdapter: BrandAdapter

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentBrandBinding
    ) {
        binding.rcvBrand.setAdapter(brandAdapter)
        binding.rcvBrand.setGridLayoutManager(2)
        val spacingInPixels = resources.getDimensionPixelOffset(R.dimen.spacing_brand_rcv)
        binding.rcvBrand.getRecyclerView().addItemDecoration(SpacesItemDecoration(spacingInPixels))
    }

    override fun initData() {
        with(viewModel) {
            getBrand()
            brand.observe(this@BrandFragment) {
                handleObjectLoadMoreResponse(it, binding.progressBar)
            }
        }
    }

    override fun <U> getListLoadMoreResponse(data: U?, isRefresh: Boolean, canLoadMore: Boolean) {
        super.getListLoadMoreResponse(data, isRefresh, canLoadMore)
        if (data is ResultResponse<*>) {
            binding.rcvBrand.refresh(data = data.results as List<Brand>)
        }
    }

    override fun initListener() {
        brandAdapter.addOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(
                adapter: RecyclerView.Adapter<*>,
                viewHolder: RecyclerView.ViewHolder?,
                viewType: Int,
                position: Int
            ) {
                transitFragment(CategoryDetailFragment(), R.id.parent_container, Bundle().apply {
                    putSerializable(BundleKey.KEY_DETAIL_CATEGORY, brandAdapter.getItem(position, Brand::class.java))
                })
            }

        })
        binding.apply {
            rcvBrand.setOnRefreshListener {
                viewModel.getBrand()
            }
            header.listener = object : HncHeaderView.IOnClickHeader {
                override fun onLeftClick() {
                    activity?.onBackPressed()
                }

                override fun onRightClick() {
                }

            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBrandBinding {
        return FragmentBrandBinding.inflate(inflater, container, false)
    }
}