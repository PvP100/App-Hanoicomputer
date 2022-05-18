package com.example.utt.hnccomputer.ui.fragment.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val cartViewModel: CartViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentCartBinding
    ) {

        initListener()

    }

    override fun initListener() {
        binding.header.listener = object : HncHeaderView.IOnClickHeader {
            override fun onLeftClick() {
                activity?.onBackPressed()
            }

            override fun onRightClick() {

            }

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCartBinding {
        return FragmentCartBinding.inflate(inflater, container, false)
    }

    override fun initData() {
        with(cartViewModel) {
            getCart()
            myOrder.observe(this@CartFragment) {
                
            }
        }
    }
}