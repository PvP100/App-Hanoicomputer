package com.example.utt.hnccomputer.ui.fragment.register

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.databinding.FragmentRegisterBinding
import com.example.utt.hnccomputer.ui.fragment.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    private val viewModel: RegisterViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentRegisterBinding
    ) {

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
            btnRegister.setOnClickListener {
//                viewModel.register(edtEmail.text.toString().trim(), edtPassword.text.toString().trim())
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }
}