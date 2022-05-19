package com.example.utt.hnccomputer.ui.fragment.confirm_order

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.databinding.FragmentConfirmOrderBinding
import com.example.utt.hnccomputer.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmOrderFragment : BaseFragment<FragmentConfirmOrderBinding>() {

    private val viewModel: ConfirmOrderViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentConfirmOrderBinding
    ) {
    }

    override fun initData() {
        with(viewModel) {
            getOrder()
            response.observe(this@ConfirmOrderFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    Log.v("phongpv", customerResponse.value?.data.toString() + myOrder.value.toString())
                }
            }
        }
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConfirmOrderBinding {
        return FragmentConfirmOrderBinding.inflate(inflater, container, false)
    }
}