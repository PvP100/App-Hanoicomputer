package com.example.utt.hnccomputer.ui.fragment.my_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.BaseFragmentStatePagerAdapter
import com.example.utt.hnccomputer.base.entity.BaseFragmentPagerModel
import com.example.utt.hnccomputer.databinding.FragmentMyOrderBinding
import com.example.utt.hnccomputer.entity.model.OrderStatus
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyOrderFragment : BaseFragment<FragmentMyOrderBinding>() {

    private val informationFragments = mutableListOf<BaseFragmentPagerModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        informationFragments.add(
            BaseFragmentPagerModel(
                OrderByTypeFragment(OrderStatus.CHECK),
                "Đã xử lý"
            )
        )
        informationFragments.add(
            BaseFragmentPagerModel(
                OrderByTypeFragment(OrderStatus.UNCHECK),
                "Chưa xử lý"
            )
        )
        informationFragments.add(
            BaseFragmentPagerModel(
                OrderByTypeFragment(OrderStatus.CANCEL),
                "Đã hủy"
            )
        )

        binding.pager.isUserInputEnabled = false

        binding.pager.adapter = context?.let {
            BaseFragmentStatePagerAdapter(
                it, informationFragments, this
            )
        }

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = informationFragments[position].title
        }.attach()
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentMyOrderBinding
    ) {

    }

    override fun initData() {
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyOrderBinding {
        return FragmentMyOrderBinding.inflate(inflater, container, false)
    }
}