package com.example.utt.hnccomputer.ui.fragment.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.customview.RegisterInputView
import com.example.utt.hnccomputer.databinding.FragmentRegisterBinding
import com.example.utt.hnccomputer.extension.changeStatusBarContrastStyle
import com.example.utt.hnccomputer.extension.showDatePickerDialog
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
                    replaceFragment(LoginFragment(), R.id.parent_container)
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
//                viewModel.register(edtEmail.text.toString().trim(), edtPassword.text.toString().trim())
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