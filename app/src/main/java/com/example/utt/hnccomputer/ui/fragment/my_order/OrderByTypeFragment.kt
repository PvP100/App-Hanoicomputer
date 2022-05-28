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
class OrderByTypeFragment : BaseViewStubFragment<FragmentOrderByTypeBinding>() {

    private val viewModel: OrderByTypeViewModel by viewModels()

    private val orderAdapter: OrderAdapter by lazy {
        OrderAdapter(requireContext(), false)
    }

    companion object {
        fun newInstance(type: OrderStatus) =
            OrderByTypeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARGS_ORDER_TYPE, type.type)
                }
            }

        private const val ARGS_ORDER_TYPE = "ARGS_ORDER_TYPE"
    }

    override fun initData() {
        with(viewModel) {
            arguments?.let {
                if (it.containsKey(ARGS_ORDER_TYPE)) {
                    viewModel.orderType = it.getInt(ARGS_ORDER_TYPE, OrderStatus.CHECK.type)
                }
            }
            getOrder(true)
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
            rcvOrder.setOnRefreshListener {
                viewModel.getOrder(true)
            }
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