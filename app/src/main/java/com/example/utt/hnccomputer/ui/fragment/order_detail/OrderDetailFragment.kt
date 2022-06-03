package com.example.utt.hnccomputer.ui.fragment.order_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.adapter.order.OrderDetailAdapter
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.adapter.RecyclerViewAdapter
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentOrderDetailBinding
import com.example.utt.hnccomputer.entity.model.OrderDetail
import com.example.utt.hnccomputer.entity.model.OrderStatus
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.entity.model.ProductOrder
import com.example.utt.hnccomputer.extension.*
import com.example.utt.hnccomputer.ui.fragment.product_detail.ProductDetailFragment
import com.example.utt.hnccomputer.utils.BundleKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderDetailFragment : BaseFragment<FragmentOrderDetailBinding>() {

    private val viewModel: OrderDetailViewModel by viewModels()

    @Inject
    lateinit var orderAdapter: OrderDetailAdapter

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentOrderDetailBinding
    ) {
        binding.rcvOrder.adapter = orderAdapter
    }

    override fun initData() {
        with(viewModel) {
            arguments?.let {
                if (it.containsKey(BundleKey.KEY_ORDER_ID)) {
                    orderId = it.getInt(BundleKey.KEY_ORDER_ID)
                    getOrderDetail()
                }
            }
            cancelDate.observe(this@OrderDetailFragment) {
                handleObjResponse(it, binding.progressBar)
            }
            orderDetail.observe(this@OrderDetailFragment) {
                handleObjResponse(it, binding.progressBar)
            }
        }
    }

    override fun <U> getObjectResponse(data: U) {
        super.getObjectResponse(data)
        if (data is OrderDetail) {
            setOrderStatus(data)
            binding.model = data
            binding.createdDate.text = "Ngày đặt hàng: ${data.order.createdDate.convertToDate()}"
            binding.total.totalPrice.text = data.totalPrice.convertToVnd()
            orderAdapter.refresh(data.listProduct)
        } else if (data is Long) {
            viewModel.orderDetail.value?.data?.let {
                it.order.isCheck = OrderStatus.CANCEL
                it.order.updateDate = data
                setOrderStatus(it)
                binding.btnCancelOrder.gone()
            }
        }
    }

    private fun setOrderStatus(data: OrderDetail) {
        when (data.order.isCheck) {
            OrderStatus.CHECK -> {
                binding.btnCancelOrder.gone()
                binding.orderStatus.tvOrderStatus.text = OrderStatus.CHECK.status
                binding.orderStatus.orderStatusDate.text = "${data.order.updateDate.convertToDate()}"
                binding.orderStatus.icOrderStatus.setImageResource(R.drawable.ic_order_status)
            }
            OrderStatus.UNCHECK -> {
                binding.orderStatus.tvOrderStatus.text = OrderStatus.UNCHECK.status
                binding.orderStatus.orderStatusDate.gone()
                binding.orderStatus.icOrderStatus.setImageResource(R.drawable.ic_order_status_yellow)
            }
            OrderStatus.CANCEL -> {
                binding.btnCancelOrder.gone()
                binding.orderStatus.tvOrderStatus.text = OrderStatus.CANCEL.status
                binding.orderStatus.orderStatusDate.visible()
                binding.orderStatus.orderStatusDate.text = "${data.order.updateDate.convertToDate()}"
                binding.orderStatus.icOrderStatus.setImageResource(R.drawable.ic_order_status_red)
            }
        }
    }

    override fun initListener() {
        binding.apply {
            btnCancelOrder.onAvoidDoubleClick {
                viewModel.cancelOrder()
            }
            header.listener = object : HncHeaderView.IOnClickHeader {
                override fun onLeftClick() {
                    activity?.onBackPressed()
                }

                override fun onRightClick() {}

            }

            orderAdapter.addOnItemClickListener(object : RecyclerViewAdapter.OnItemClickListener {
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
                            putString(BundleKey.KEY_PRODUCT_DETAIL, orderAdapter.getItem(position, ProductOrder::class.java)?.productId)
                        }
                    )
                }

            })
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOrderDetailBinding {
        return FragmentOrderDetailBinding.inflate(inflater, container, false)
    }
}