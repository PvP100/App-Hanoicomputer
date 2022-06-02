package com.example.utt.hnccomputer.ui.fragment.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.entity.BaseError
import com.example.utt.hnccomputer.databinding.FragmentLoginBinding
import com.example.utt.hnccomputer.extension.onAvoidDoubleClick
import com.example.utt.hnccomputer.extension.openActivityWithoutAddtoBackstack
import com.example.utt.hnccomputer.extension.toast
import com.example.utt.hnccomputer.ui.activity.main.MainActivity
import com.example.utt.hnccomputer.ui.dialog.ErrorResponseDialog
import com.example.utt.hnccomputer.ui.fragment.main.MainFragment
import com.example.utt.hnccomputer.ui.fragment.register.RegisterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentLoginBinding
    ) {

    }

    override fun initData() {
        with(viewModel) {
            response.observe(this@LoginFragment) {
                handleNoDataResponse(it, binding.progressBar) {
                    replaceFragment(MainFragment(), R.id.parent_container)
                }
            }
        }
    }

    override fun handleValidateError(throwable: BaseError?) {
        throwable?.error?.let {
            errorDialog.setErrorMessage(it)
            errorDialog.show(childFragmentManager, errorDialog.tag)
        }
    }

    override fun initListener() {
        binding.apply {
            btnLogin.onAvoidDoubleClick {
                viewModel.login(edtUsername.text.toString().trim(), edtPassword.text.toString().trim())
            }
            tvNotHaveAccount.setOnClickListener {
                transitFragment(RegisterFragment(), R.id.parent_container)
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
    }
}