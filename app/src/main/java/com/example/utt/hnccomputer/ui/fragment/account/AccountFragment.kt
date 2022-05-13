package com.example.utt.hnccomputer.ui.fragment.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.databinding.FragmentAccountBinding
import com.example.utt.hnccomputer.ui.fragment.account_information.AccountInformationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    private val viewModel: AccountViewModel by viewModels()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentAccountBinding
    ) {

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountBinding {
        return FragmentAccountBinding.inflate(inflater, container, false)
    }

    override fun initListener() {
        binding.apply {
            btnProfile.layout.setOnClickListener {
                transitFragment(AccountInformationFragment(), R.id.parent_container)
            }
        }
    }

    override fun initData() {
        with(viewModel) {

        }
    }
}