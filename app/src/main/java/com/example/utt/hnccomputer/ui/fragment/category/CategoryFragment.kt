package com.example.utt.hnccomputer.ui.fragment.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.BaseViewStubFragment
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.adapter.category.CategoryAdapter
import com.example.utt.hnccomputer.databinding.FragmentCategoryBinding
import com.example.utt.hnccomputer.entity.model.Category
import com.example.utt.hnccomputer.entity.response.ResultResponse
import com.example.utt.hnccomputer.ui.fragment.category_detail.CategoryDetailFragment
import com.example.utt.hnccomputer.utils.BundleKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CategoryFragment : BaseViewStubFragment<FragmentCategoryBinding>() {

    private val viewModel: CategoryViewModel by viewModels()

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    override fun initListener() {
        binding.apply {
            rcvCategory.setOnRefreshListener {
                viewModel.getCategory()
            }
            categoryAdapter.addOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(
                    adapter: RecyclerView.Adapter<*>,
                    viewHolder: RecyclerView.ViewHolder?,
                    viewType: Int,
                    position: Int
                ) {
                    transitFragment(CategoryDetailFragment(), R.id.parent_container, Bundle().apply {
                        putSerializable(BundleKey.KEY_DETAIL_CATEGORY, categoryAdapter.getItem(position, Category::class.java))
                    })
                }
            })
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
        binding.rcvCategory.setAdapter(categoryAdapter)
        binding.rcvCategory.setListLayoutManager(LinearLayout.VERTICAL)
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