package com.example.utt.hnccomputer.ui.fragment.my_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import com.airbnb.lottie.LottieAnimationView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.adapter.order.OrderAdapter
import com.example.utt.hnccomputer.base.BaseViewStubFragment
import com.example.utt.hnccomputer.base.entity.BaseObjectLoadMoreResponse
import com.example.utt.hnccomputer.databinding.FragmentOrderByTypeBinding
import com.example.utt.hnccomputer.entity.model.Order
import com.example.utt.hnccomputer.entity.model.OrderStatus
import com.example.utt.hnccomputer.entity.response.ResultResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderByTypeFragment(private val orderType: OrderStatus) : BaseViewStubFragment<FragmentOrderByTypeBinding>() {

    private val viewModel: OrderByTypeViewModel by viewModels()

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter(requireContext(), false)
    }

    override fun initData() {
        with(viewModel) {
            getOrder(orderType)
            order.observe(this@OrderByTypeFragment) {
                handleObjectLoadMoreResponse(it, binding.progressBar)
            }
        }
    }

    override fun <U> getListLoadMoreResponse(data: U?, isRefresh: Boolean, canLoadMore: Boolean) {
        if (data is ResultResponse<*>) {
            if (isRefresh) {
                binding.rcvOrder.refresh(data.results as List<Order>)
            } else {
                binding.rcvOrder.addItem(data.results as List<Order>)
            }
            binding.rcvOrder.enableLoadMore(canLoadMore)
        }
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderByTypeBinding {
        return FragmentOrderByTypeBinding.inflate(inflater, container, false)
    }

    override fun onCreateViewAfterViewStubInflated(
        inflatedView: View,
        savedInstanceState: Bundle?
    ) {
        binding.rcvOrder.setAdapter(orderAdapter)
        binding.rcvOrder.setListLayoutManager(LinearLayout.VERTICAL)
    }

    override fun getViewStubLayoutResource(): Int {
        return R.layout.fragment_order_by_type
    }
}