package com.example.utt.hnccomputer.ui.fragment.confirm_order

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentConfirmOrderBinding
import com.example.utt.hnccomputer.databinding.FragmentProductDetailBinding
import com.example.utt.hnccomputer.extension.convertToVnd
import com.example.utt.hnccomputer.extension.onAvoidDoubleClick
import com.example.utt.hnccomputer.utils.BundleKey
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmOrderFragment : BaseFragment<FragmentConfirmOrderBinding>() {

    private val viewModel: ConfirmOrderViewModel by viewModels()

    private var totalPrice: Long = 0

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentConfirmOrderBinding
    ) {
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
                    customerResponse.value?.data?.let { customer ->
                        binding.model = customer
                    }
                    binding.bottom.totalPrice = totalPrice.convertToVnd()
                }
            }
        }
    }

    override fun initListener() {
        binding.apply {
            bottom.btnPlaceOrder.onAvoidDoubleClick {

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