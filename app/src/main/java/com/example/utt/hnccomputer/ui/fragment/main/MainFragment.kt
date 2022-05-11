package com.example.utt.hnccomputer.ui.fragment.main

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.base.BaseFragmentStatePagerAdapter
import com.example.utt.hnccomputer.base.entity.BaseFragmentPagerModel
import com.example.utt.hnccomputer.databinding.MainFragmentBinding
import com.example.utt.hnccomputer.ui.dialog.LoginDialog
import com.example.utt.hnccomputer.ui.fragment.account.AccountFragment
import com.example.utt.hnccomputer.ui.fragment.category.CategoryFragment
import com.example.utt.hnccomputer.ui.fragment.home.HomeFragment
import com.example.utt.hnccomputer.ui.fragment.login.LoginFragment

class MainFragment : BaseFragment<MainFragmentBinding>(){

    companion object {
        const val PAGE_HOME = 0
        const val PAGE_CATEGORY = 1
        const val PAGE_ACCOUNT = 2
    }

    private val mainFragments = mutableListOf<BaseFragmentPagerModel>()

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: MainFragmentBinding
    ) {
        createFragment()
        binding.viewPager.adapter =
            BaseFragmentStatePagerAdapter(requireContext(), mainFragments, childFragmentManager, lifecycle)
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.navigation.menu.getItem(position).isChecked = true
            }
        })
        binding.navigation.setOnItemSelectedListener(this::navigationItemSelected)
    }

    private fun navigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                binding.viewPager.setCurrentItem(PAGE_HOME, false)
            }
            R.id.navigation_category -> {
                binding.viewPager.setCurrentItem(PAGE_CATEGORY, false)
            }
            R.id.navigation_account -> {
                val loginDialog = LoginDialog()
                loginDialog.setOnDialogListener(object : LoginDialog.OnDialogListener {
                    override fun onLogin() {
                        transitFragment(LoginFragment(), R.id.parent_container)
                        loginDialog.dismiss()
                    }
                })
                loginDialog.show(childFragmentManager, loginDialog.tag)
//                viewDataBinding.viewPager.setCurrentItem(PAGE_ACCOUNT, false)
            }
            else -> return false
        }
        return true
    }

    private fun createFragment() {
        mainFragments.add(
            BaseFragmentPagerModel(
                HomeFragment()
            )
        )
        mainFragments.add(
            BaseFragmentPagerModel(
                CategoryFragment()
            )
        )
        mainFragments.add(
            BaseFragmentPagerModel(
                AccountFragment()
            )
        )
    }

    override fun initData() {

    }

    override fun initListener() {

    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): MainFragmentBinding {
        return MainFragmentBinding.inflate(inflater, container, false)
    }
}