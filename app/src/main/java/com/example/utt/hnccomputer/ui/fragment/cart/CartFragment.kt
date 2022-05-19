package com.example.utt.hnccomputer.ui.fragment.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.adapter.cart.CartAdapter
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.BaseViewModel
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.database.entity.MyOrderInformation
import com.example.utt.hnccomputer.databinding.FragmentCartBinding
import com.example.utt.hnccomputer.extension.convertToVnd
import com.example.utt.hnccomputer.ui.fragment.confirm_order.ConfirmOrderFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>() {

    private val cartViewModel: CartViewModel by viewModels()

    private var isDelete = false

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
            btnBuy.setOnClickListener {
                transitFragment(
                    ConfirmOrderFragment(),
                    R.id.parent_container
                )
            }
            cartAdapter.onRemoveCart = { id, position ->
                cartViewModel.position = position
                isDelete = true
                id?.let { it1 -> cartViewModel.removeCart(productId = it1) }
            }
            cartAdapter.onCountQuantity = { quantity, productId ->
                isDelete = false
                cartViewModel.updateCart(quantity, productId)
                setTotalPrice()
            }
        }
    }

    private fun setTotalPrice() {
        binding.tvTotalCart.text = cartAdapter.getTotal().convertToVnd()
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
                setTotalPrice()
            }
            response.observe(this@CartFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    if (isDelete) {
                        cartAdapter.removeModel(cartViewModel.position)
                        setTotalPrice()
                    }
                }
            }
        }
    }
}