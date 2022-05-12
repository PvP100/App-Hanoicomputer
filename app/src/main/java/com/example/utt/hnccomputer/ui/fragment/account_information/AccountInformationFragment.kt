package com.example.utt.hnccomputer.ui.fragment.account_information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.databinding.FragmentAccountInformationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountInformationFragment : BaseFragment<FragmentAccountInformationBinding>() {

    private val viewModel: AccountInformationViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentAccountInformationBinding
    ) {

    }

    override fun initData() {
        with(viewModel) {

        }
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountInformationBinding {
        return FragmentAccountInformationBinding.inflate(inflater, container, false)
    }


}