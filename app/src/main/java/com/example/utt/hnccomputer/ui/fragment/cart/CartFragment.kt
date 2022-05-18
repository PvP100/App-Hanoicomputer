package com.example.utt.hnccomputer.ui.fragment.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.adapter.cart.CartAdapter
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val cartViewModel: CartViewModel by viewModels()

    private val cartAdapter: CartAdapter by lazy {
        CartAdapter(requireContext())
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentCartBinding
    ) {
        binding.rcvCart.adapter = cartAdapter
        initListener()

    }

    override fun initListener() {
        binding.apply {
            header.listener = object : HncHeaderView.IOnClickHeader {
                override fun onLeftClick() {
                    activity?.onBackPressed()
                }

                override fun onRightClick() {

                }
            }
            cartAdapter.onRemoveCart = { id, position ->
                cartViewModel.position = position
                id?.let { it1 -> cartViewModel.removeCart(productId = it1) }
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
                cartAdapter.refresh(it)
            }
            response.observe(this@CartFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    cartAdapter.removeModel(cartViewModel.position)
                }
            }
        }
    }
}