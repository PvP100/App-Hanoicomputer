package com.example.utt.hnccomputer.ui.fragment.product_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.entity.BaseError
import com.example.utt.hnccomputer.databinding.FragmentProductDetailBinding
import com.example.utt.hnccomputer.entity.model.Product
import com.example.utt.hnccomputer.extension.*
import com.example.utt.hnccomputer.ui.dialog.LoginDialog
import com.example.utt.hnccomputer.ui.fragment.cart.CartFragment
import com.example.utt.hnccomputer.ui.fragment.login.LoginFragment
import com.example.utt.hnccomputer.utils.BundleKey
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : BaseFragment<FragmentProductDetailBinding>() {

    private val viewModel: ProductDetailViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentProductDetailBinding
    ) {
        (activity as AppCompatActivity).changeStatusBarContrastStyle(false)
        arguments?.let { 
            if (it.containsKey(BundleKey.KEY_PRODUCT_DETAIL)) {
                it.getString(BundleKey.KEY_PRODUCT_DETAIL)
                    ?.let { it1 -> viewModel.getProductDetail(it1) }
            }
        }
    }

    override fun initData() {
        with(viewModel) {
            product.observe(this@ProductDetailFragment) {
                handleObjResponse(it, binding.progressBar)
            }
            response.observe(this@ProductDetailFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    toast("Thêm thành công")
                }
            }
        }
    }

    override fun <U> getObjectResponse(data: U) {
        super.getObjectResponse(data)
        if (data is Product) {
            binding.apply {
                imgProduct.loadImage(data.logoUrl)
                tvDescription.text = data.description
                if (data.quantity > 0) {
                    status.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_green))
                    status.text = "Còn hàng"
                } else {
                    status.text = "Hết hàng"
                    status.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_red_01))
                }
                productName.text = data.name
                bottom.tvPrice.text = data.price.convertToVnd()
                tvWarranty.text = "Bảo hành: ${data.warranty}"
            }
        }
    }

    override fun initListener() {
        binding.apply {
            icBack.setOnClickListener {
                activity?.onBackPressed()
            }
            icCart.setOnClickListener {
                if (viewModel.checkLogin()) {
                    transitFragment(CartFragment(), R.id.parent_container)
                } else {
                    val loginDialog = LoginDialog()
                    loginDialog.setOnDialogListener(object : LoginDialog.OnDialogListener {
                        override fun onLogin() {
                            transitFragment(LoginFragment(), R.id.parent_container)
                            loginDialog.dismiss()
                        }
                    })
                    loginDialog.show(childFragmentManager, loginDialog.tag)
                }
            }
            bottom.btnAddToCart.onAvoidDoubleClick {
                viewModel.addToCart()
            }
        }
    }

    override fun handleValidateError(throwable: BaseError?) {
        throwable?.error?.let {
            toast(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).changeStatusBarContrastStyle(true)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductDetailBinding {
        return FragmentProductDetailBinding.inflate(inflater, container, false)
    }
}