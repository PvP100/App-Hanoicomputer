package com.example.utt.hnccomputer.ui.fragment.my_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseViewStubFragment
import com.example.utt.hnccomputer.databinding.FragmentOrderByTypeBinding
import com.example.utt.hnccomputer.entity.model.OrderType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderByTypeFragment(private val orderType: OrderType) : BaseViewStubFragment<FragmentOrderByTypeBinding>() {

    private val viewModel: OrderByTypeViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentOrderByTypeBinding
    ) {

    }

    override fun initData() {
        with(viewModel) {
            type == orderType
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

    }

    override fun getViewStubLayoutResource(): Int {
        return R.layout.fragment_order_by_type
    }
}