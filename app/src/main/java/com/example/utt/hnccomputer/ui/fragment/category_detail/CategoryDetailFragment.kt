package com.example.utt.hnccomputer.ui.fragment.category_detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.adapter.home.CategoryProductAdapter
import com.example.utt.hnccomputer.adapter.home.HomeProductAdapter
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentCategoryDetailBinding
import com.example.utt.hnccomputer.entity.model.Brand
import com.example.utt.hnccomputer.entity.model.Category
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.extension.toast
import com.example.utt.hnccomputer.ui.dialog.FilterProductDialog
import com.example.utt.hnccomputer.ui.fragment.brand.BrandViewModel
import com.example.utt.hnccomputer.ui.fragment.category.CategoryViewModel
import com.example.utt.hnccomputer.utils.BundleKey
import com.example.utt.hnccomputer.utils.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryDetailFragment : BaseFragment<FragmentCategoryDetailBinding>() {

    private val categoryDetailViewModel: CategoryDetailViewModel by viewModels()

    private val brandViewModel: BrandViewModel by viewModels()

    private val categoryViewModel: CategoryViewModel by viewModels()

    private val dialogFilter = FilterProductDialog()

    private var isBrand = false

    @Inject
    lateinit var productAdapter: CategoryProductAdapter

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
                if (it.getSerializable(BundleKey.KEY_DETAIL_CATEGORY) is Category) {
                    categoryDetailViewModel.categoryId = (it.getSerializable(BundleKey.KEY_DETAIL_CATEGORY) as Category).id
                    binding.header.setHeaderTitle((it.getSerializable(BundleKey.KEY_DETAIL_CATEGORY) as Category).title)
                } else {
                    isBrand = true
                    categoryDetailViewModel.brandId = (it.getSerializable(BundleKey.KEY_DETAIL_CATEGORY) as Brand).id
                    binding.header.setHeaderTitle((it.getSerializable(BundleKey.KEY_DETAIL_CATEGORY) as Brand).brandName)
                }
            }
        }
    }

    override fun initData() {
        with(categoryDetailViewModel) {
            getProduct()
            response.observe(this@CategoryDetailFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    toast("Thêm thành công")
                }
            }
            product.observe(this@CategoryDetailFragment) {
                handleObjectLoadMoreResponse(it, binding.progressBar)
            }
        }
        with(brandViewModel) {
            brand.observe(this@CategoryDetailFragment) {
                handleObjectLoadMoreResponse(it, binding.progressBar)
            }
        }
        with(categoryViewModel) {
            category.observe(this@CategoryDetailFragment) {
                handleObjectLoadMoreResponse(it, binding.progressBar)
            }
        }
    }

    override fun <U> getListLoadMoreResponse(data: U?, isRefresh: Boolean, canLoadMore: Boolean) {
        super.getListLoadMoreResponse(data, isRefresh, canLoadMore)
        if (data is ResultResponse<*>) {
            when {
                data.results.firstOrNull() is Brand -> {
                    dialogFilter.setListBrand(data.results as List<Brand>)
                    dialogFilter.show(childFragmentManager, dialogFilter.tag)
                }
                data.results.firstOrNull() is Category -> {
                    dialogFilter.setListCategory(data.results as List<Category>)
                    dialogFilter.show(childFragmentManager, dialogFilter.tag)
                }
                else -> {
                    binding.rcvProduct.refresh(data = data.results as List<Product>)
                }
            }
        }
    }

    override fun initListener() {
        binding.apply {
            productAdapter.addToCart = {
                categoryDetailViewModel.addToCart(it)
            }
            rcvProduct.setOnRefreshListener {
                categoryDetailViewModel.getProduct(isRefresh = true)
            }
            filterPrice.onReturnSelected = {
                when(it) {
                    1 -> {
                        categoryDetailViewModel.isSale = 1
                    }
                    2 -> {
                        categoryDetailViewModel.sortBy = "price"
                        categoryDetailViewModel.sortType = "asc"
                    }
                    3 -> {
                        categoryDetailViewModel.sortBy = "price"
                        categoryDetailViewModel.sortType = "desc"
                    }
                    else -> {
                        categoryDetailViewModel.isSale = 0
                        categoryDetailViewModel.sortBy = "createdDate"
                        categoryDetailViewModel.sortType = "desc"
                    }
                }
                categoryDetailViewModel.getProduct()
            }
            header.listener = object : HncHeaderView.IOnClickHeader {
                override fun onLeftClick() {
                    activity?.onBackPressed()
                }

                override fun onRightClick() {
                    if (isBrand) {
                        categoryViewModel.getCategory()
                    } else {
                        brandViewModel.getBrand()
                    }
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