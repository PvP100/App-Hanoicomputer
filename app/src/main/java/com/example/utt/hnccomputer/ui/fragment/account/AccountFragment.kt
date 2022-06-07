package com.example.utt.hnccomputer.ui.fragment.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.BaseViewStubFragment
import com.example.utt.hnccomputer.base.entity.BaseError
import com.example.utt.hnccomputer.base.permission.PermissionHelper
import com.example.utt.hnccomputer.databinding.FragmentAccountBinding
import com.example.utt.hnccomputer.entity.model.Customer
import com.example.utt.hnccomputer.extension.goToGallery
import com.example.utt.hnccomputer.extension.loadImage
import com.example.utt.hnccomputer.extension.openGallery
import com.example.utt.hnccomputer.ui.activity.main.MainActivity
import com.example.utt.hnccomputer.ui.dialog.PickUpAvatarDialog
import com.example.utt.hnccomputer.ui.fragment.account_information.AccountInformationFragment
import com.example.utt.hnccomputer.ui.fragment.main.MainFragment
import com.example.utt.hnccomputer.ui.fragment.my_order.MyOrderFragment
import com.example.utt.hnccomputer.utils.BundleKey
import com.example.utt.hnccomputer.utils.BundleKey.REQUEST_CODE_IMAGE_STORAGE
import com.example.utt.hnccomputer.utils.RealPathUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AccountFragment : BaseViewStubFragment<FragmentAccountBinding>() {

    private val viewModel: AccountViewModel by viewModels()

    val dialog = PickUpAvatarDialog()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountBinding {
        return FragmentAccountBinding.inflate(inflater, container, false)
    }

    override fun initListener() {
        binding.apply {
            refreshLayout.setOnRefreshListener {
                viewModel.getCustomerInformation(true)
            }
            cardView.setOnClickListener {
                goToGallery(permissionHelper = (requireActivity() as MainActivity).permissionHelper, listener = {
                    openGallery(REQUEST_CODE_IMAGE_STORAGE)
                })
            }
            btnProfile.layout.setOnClickListener {
                transitFragment(AccountInformationFragment(), R.id.parent_container)
            }
            btnOrder.layout.setOnClickListener {
                transitFragment(MyOrderFragment(), R.id.parent_container)
            }
            btnLogout.setOnClickListener {
                viewModel.logout()
                replaceFragment(MainFragment(), R.id.parent_container)
            }
        }
    }

    override fun handleValidateError(throwable: BaseError?) {
        throwable?.error?.let {
            errorDialog.setErrorMessage(it)
            errorDialog.show(childFragmentManager, errorDialog.tag)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            BundleKey.REQUEST_CODE_IMAGE_STORAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val listUri = data?.data ?: return
                    viewModel.avatarPath = RealPathUtil.getRealPath(requireContext(), listUri)
                    viewModel.uploadAvatar()
                }
            }
        }
    }

    override fun initData() {

    }

    override fun onCreateViewAfterViewStubInflated(
        inflatedView: View,
        savedInstanceState: Bundle?
    ) {
        with(viewModel) {
            getCustomerInformation()
            customerResponse.observe(this@AccountFragment) {
                handleObjResponse(it, binding.progressBar)
            }
            response.observe(this@AccountFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    binding.imgAvaProfile.loadImage(viewModel.avatarPath)
                }
            }
        }
    }

    override fun <U> getObjectResponse(data: U) {
        if (data is Customer) {
            binding.refreshLayout.isRefreshing = false
            binding.model = data
            binding.imgAvaProfile.loadImage("${data.avatarUrl}?${Random().nextInt(100)}")
        }
    }

    override fun getViewStubLayoutResource(): Int {
        return R.layout.fragment_account
    }
}