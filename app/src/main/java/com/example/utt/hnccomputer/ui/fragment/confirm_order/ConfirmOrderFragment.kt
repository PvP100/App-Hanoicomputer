package com.example.utt.hnccomputer.ui.fragment.confirm_order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.adapter.confirm_order.ConfirmOrderAdapter
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentConfirmOrderBinding
import com.example.utt.hnccomputer.extension.convertToVnd
import com.example.utt.hnccomputer.extension.onAvoidDoubleClick
import com.example.utt.hnccomputer.ui.fragment.main.MainFragment
import com.example.utt.hnccomputer.utils.BundleKey
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmOrderFragment : BaseFragment<FragmentConfirmOrderBinding>() {

    private val viewModel: ConfirmOrderViewModel by viewModels()

    private val adapter: ConfirmOrderAdapter by lazy {
        ConfirmOrderAdapter(requireContext())
    }

    private var isCreate = false

    private var totalPrice: Long = 0

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentConfirmOrderBinding
    ) {
        binding.rcvCart.adapter = adapter
        arguments?.let {
            if (it.containsKey(BundleKey.KEY_TOTAL_PRICE)) {
                totalPrice = it.getLong(BundleKey.KEY_TOTAL_PRICE)
            }
        }
    }

    override fun initData() {
        with(viewModel) {
            getOrder()
            response.observe(this@ConfirmOrderFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    if (isCreate) {
                         viewModel.clearLocalDatabase()
                        isCreate = false
                        replaceFragment(MainFragment(), R.id.parent_container)
                    } else {
                        customerResponse.value?.data?.let { customer ->
                            binding.model = customer
                        }
                        myOrder.value?.let { it1 -> adapter.refresh(it1) }
                        binding.bottom.totalPrice = totalPrice.convertToVnd()
                    }
                }
            }
        }
    }

    override fun initListener() {
        binding.apply {
            bottom.btnPlaceOrder.onAvoidDoubleClick {
                isCreate = true
                viewModel.createOrder()
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
    ): FragmentConfirmOrderBinding {
        return FragmentConfirmOrderBinding.inflate(inflater, container, false)
    }
}