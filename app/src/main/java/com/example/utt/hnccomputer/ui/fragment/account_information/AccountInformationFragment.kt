package com.example.utt.hnccomputer.ui.fragment.account_information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentAccountInformationBinding
import com.example.utt.hnccomputer.entity.model.Customer
import com.example.utt.hnccomputer.extension.onAvoidDoubleClick
import com.example.utt.hnccomputer.ui.dialog.ChangePasswordDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountInformationFragment : BaseFragment<FragmentAccountInformationBinding>() {

    private val viewModel: AccountInformationViewModel by viewModels()

    private val changePasswordDialog: ChangePasswordDialog by lazy {
        ChangePasswordDialog()
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentAccountInformationBinding
    ) {

    }

    override fun initData() {
        with(viewModel) {
            getCustomerInformation()
            customerResponse.observe(this@AccountInformationFragment) {
                handleObjResponse(it, binding.progressBar)
            }
            response.observe(this@AccountInformationFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    changePasswordDialog.dismiss()
                }
            }
        }
    }

    override fun <U> getObjectResponse(data: U) {
        super.getObjectResponse(data)
        if (data is Customer) {
            binding.model = data
        }
    }

    override fun initListener() {
        binding.apply {
            changePasswordDialog.setOnChangePasswordListener(object : ChangePasswordDialog.OnChangePassword {
                override fun onChangePassword(oldPassword: String, newPassword: String) {
                    viewModel.changePassword(newPassword, oldPassword)
                }

            })
            header.listener = object : HncHeaderView.IOnClickHeader {
                override fun onLeftClick() {
                    activity?.onBackPressed()
                }

                override fun onRightClick() {

                }
            }
            layoutChangePassword.onAvoidDoubleClick {
                changePasswordDialog.show(childFragmentManager, changePasswordDialog.tag)
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountInformationBinding {
        return FragmentAccountInformationBinding.inflate(inflater, container, false)
    }


}