package com.example.utt.hnccomputer.ui.fragment.order_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.adapter.order.OrderDetailAdapter
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentOrderDetailBinding
import com.example.utt.hnccomputer.entity.model.OrderDetail
import com.example.utt.hnccomputer.extension.convertToDate
import com.example.utt.hnccomputer.extension.convertToVnd
import com.example.utt.hnccomputer.utils.BundleKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding>() {

    private val viewModel: OrderDetailViewModel by viewModels()

    @Inject
    lateinit var adapter: OrderDetailAdapter

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentOrderDetailBinding
    ) {
        binding.rcvOrder.adapter = adapter
    }

    override fun initData() {
        with(viewModel) {
            arguments?.let {
                if (it.containsKey(BundleKey.KEY_ORDER_ID)) {
                    getOrderDetail(it.getInt(BundleKey.KEY_ORDER_ID))
                }
            }
            orderDetail.observe(this@OrderDetailFragment) {
                handleObjResponse(it, binding.progressBar)
            }
        }
    }

    override fun <U> getObjectResponse(data: U) {
        super.getObjectResponse(data)
        if (data is OrderDetail) {
            binding.model = data
            binding.createdDate.text = "Ngày đặt hàng: ${data.order.createdDate.convertToDate()}"
            binding.total.totalPrice.text = data.totalPrice.convertToVnd()
            adapter.refresh(data.listProduct)
        }
    }

    override fun initListener() {
        binding.apply {
            header.listener = object : HncHeaderView.IOnClickHeader {
                override fun onLeftClick() {
                    activity?.onBackPressed()
                }

                override fun onRightClick() {}

            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderDetailBinding {
        return FragmentOrderDetailBinding.inflate(inflater, container, false)
    }
}