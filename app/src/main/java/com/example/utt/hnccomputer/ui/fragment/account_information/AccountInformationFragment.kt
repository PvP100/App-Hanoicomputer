package com.example.utt.hnccomputer.ui.fragment.account_information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.customview.DetailInformationView
import com.example.utt.hnccomputer.customview.HncHeaderView
import com.example.utt.hnccomputer.databinding.FragmentAccountInformationBinding
import com.example.utt.hnccomputer.entity.model.Customer
import com.example.utt.hnccomputer.extension.onAvoidDoubleClick
import com.example.utt.hnccomputer.extension.showDatePickerDialog
import com.example.utt.hnccomputer.ui.dialog.ChangePasswordDialog
import com.example.utt.hnccomputer.ui.dialog.EditNormalInformationDialog
import com.example.utt.hnccomputer.ui.dialog.SelectOptionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountInformationFragment : BaseFragment<FragmentAccountInformationBinding>() {

    private val viewModel: AccountInformationViewModel by viewModels()

    private val filterDialog = SelectOptionDialog()

    private val fullNameDialog: EditNormalInformationDialog = EditNormalInformationDialog()
    private val addressDialog: EditNormalInformationDialog = EditNormalInformationDialog()
    private val emailDialog: EditNormalInformationDialog = EditNormalInformationDialog()
    private val phoneDialog: EditNormalInformationDialog = EditNormalInformationDialog()

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
            listFilter.observe(viewLifecycleOwner) {
                filterDialog.setFilter(it)
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
        filterDialog.onReturnFilterId = { id, name ->
            filterDialog.dismiss()
            viewModel.editCustomerInformation(id.toString())
        }
        fullNameDialog.setListener(object : EditNormalInformationDialog.OnUpdateListener {
            override fun onUpdate(text: String) {
                viewModel.editCustomerInformation(text)
                fullNameDialog.dismiss()
            }
        })
        addressDialog.setListener(object : EditNormalInformationDialog.OnUpdateListener {
            override fun onUpdate(text: String) {
                viewModel.editCustomerInformation(text)
                addressDialog.dismiss()
            }
        })
        phoneDialog.setListener(object : EditNormalInformationDialog.OnUpdateListener {
            override fun onUpdate(text: String) {
                viewModel.editCustomerInformation(text)
                phoneDialog.dismiss()
            }
        })
        emailDialog.setListener(object : EditNormalInformationDialog.OnUpdateListener {
            override fun onUpdate(text: String) {
                viewModel.editCustomerInformation(text)
                emailDialog.dismiss()
            }
        })
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
            fullName.setListener(object : DetailInformationView.OnDetailClick {
                override fun onClick() {
                    viewModel.editType = fullName.getUpdateType()
                    fullNameDialog.setDetail("Họ và tên", fullName.getInformation())
                    fullNameDialog.show(childFragmentManager, fullNameDialog.tag)
                }
            })
            email.setListener(object : DetailInformationView.OnDetailClick {
                override fun onClick() {
                    viewModel.editType = email.getUpdateType()
                    emailDialog.setDetail("Email", email.getInformation())
                    emailDialog.show(childFragmentManager, fullNameDialog.tag)
                }
            })
            phoneNumber.setListener(object : DetailInformationView.OnDetailClick {
                override fun onClick() {
                    viewModel.editType = phoneNumber.getUpdateType()
                    phoneDialog.setDetail("Số điện thoại", phoneNumber.getInformation(), true)
                    phoneDialog.show(childFragmentManager, fullNameDialog.tag)
                }
            })
            address.setListener(object : DetailInformationView.OnDetailClick {
                override fun onClick() {
                    viewModel.editType = address.getUpdateType()
                    addressDialog.setDetail("Địa chỉ", address.getInformation())
                    addressDialog.show(childFragmentManager, fullNameDialog.tag)
                }
            })
            gender.setListener(object : DetailInformationView.OnDetailClick {
                override fun onClick() {
                    viewModel.editType = gender.getUpdateType()
                    filterDialog.show(childFragmentManager, filterDialog.tag)
                }
            })
            birthday.setListener(object : DetailInformationView.OnDetailClick {
                override fun onClick() {
                    viewModel.editType = birthday.getUpdateType()
                    requireContext().showDatePickerDialog {
                        viewModel.editCustomerInformation(it)
                    }
                }
            })
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