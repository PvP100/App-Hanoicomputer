package com.example.utt.hnccomputer.ui.fragment.register

import android.util.Patterns
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.customview.RegisterInputView
import com.example.utt.hnccomputer.databinding.FragmentRegisterBinding
import com.example.utt.hnccomputer.extension.changeStatusBarContrastStyle
import com.example.utt.hnccomputer.extension.showDatePickerDialog
import com.example.utt.hnccomputer.extension.toast
import com.example.utt.hnccomputer.ui.fragment.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_account_information.*

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentRegisterBinding
    ) {
        (activity as AppCompatActivity).changeStatusBarContrastStyle(false)
    }

    override fun initData() {
        with(viewModel) {
            response.observe(this@RegisterFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    activity?.onBackPressed()
                }
            }
        }
    }

    override fun initListener() {
        binding.apply {
            gender.setOnEdtClick(object : RegisterInputView.OnEdtClick {
                override fun onClick() {

                }
            })
            btnLogin.setOnClickListener {
                activity?.onBackPressed()
            }
            btnRegister.setOnClickListener {
                if (isValid()) {
                    viewModel.register(
                        email.getText().trim(),
                        password.getText().trim(),
                        fullName = fullName.getText().trim(),
                        birthday = birthday.getText().trim(),
                        phoneNumber = phoneNumber.getText().trim(),
                        address = address.getText().trim(),
                        gender = gender.getText().trim().toInt()
                    )
                }
            }
        }
    }

    private fun isValid(): Boolean {
        binding.apply {
            if (
                email.getText().trim().isNotEmpty()
                && password.getText().trim().isNotEmpty()
                && fullName.getText().trim().isNotEmpty()
                && birthday.getText().trim().isNotEmpty()
                && phoneNumber.getText().trim().isNotEmpty()
                && address.getText().trim().isNotEmpty()
            ) {
                if (email.getText().trim().isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.getText().trim()).matches()) {
                    toast("Vui lòng nhập đúng định dạng email")
                    return false
                }
                if (password.getText().trim().length < 8) {
                    toast("Nhập mật nhiều hơn 8 kí tự")
                    return false
                } else {
                    if (password.getText().trim() != rePassword.getText().trim()) {
                        toast("Mật khẩu và mật khẩu nhập lại không khớp")
                        return false
                    }
                }
                return true
            } else {
                toast("Vui lòng nhập đầy đủ thông tin")
                return false
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).changeStatusBarContrastStyle(true)
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }
}