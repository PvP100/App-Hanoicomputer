package com.example.utt.hnccomputer.ui.activity.main

import android.view.MenuItem
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.utt.hnccomputer.base.BaseDataBindingActivity
import com.example.utt.hnccomputer.base.BaseFragmentStatePagerAdapter
import com.example.utt.hnccomputer.base.entity.BaseFragmentPagerModel
import com.example.utt.hnccomputer.extension.enableFullScreen
import com.example.utt.hnccomputer.ui.fragment.account.AccountFragment
import com.example.utt.hnccomputer.ui.fragment.category.CategoryFragment
import com.example.utt.hnccomputer.ui.fragment.home.HomeFragment
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.databinding.ActivityMainBinding
import com.example.utt.hnccomputer.extension.*
import com.example.utt.hnccomputer.ui.dialog.LoginDialog
import com.example.utt.hnccomputer.ui.fragment.brand.BrandFragment
import com.example.utt.hnccomputer.ui.fragment.splash.SplashFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseDataBindingActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    private val mainFragments = mutableListOf<BaseFragmentPagerModel>()

    companion object {
        const val PAGE_HOME = 0
        const val PAGE_CATEGORY = 1
        const val PAGE_ACCOUNT = 2
    }

    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        enableFullScreen()
        createFragment()
        viewDataBinding.viewPager.adapter =
            BaseFragmentStatePagerAdapter(this, mainFragments, supportFragmentManager, lifecycle)
        viewDataBinding.viewPager.isUserInputEnabled = false
        viewDataBinding.viewPager.offscreenPageLimit = 3
        viewDataBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewDataBinding.navigation.menu.getItem(position).isChecked = true
            }
        })
        viewDataBinding.navigation.setOnItemSelectedListener(this::navigationItemSelected)
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

    private fun navigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_home -> {
                viewDataBinding.viewPager.setCurrentItem(PAGE_HOME, false)
            }
            R.id.navigation_category -> {
                viewDataBinding.viewPager.setCurrentItem(PAGE_CATEGORY, false)
            }
            R.id.navigation_account -> {
                val loginDialog = LoginDialog()
                loginDialog.setOnDialogListener(object : LoginDialog.OnDialogListener {
                    override fun onLogin() {
                        transition(
                            BrandFragment(),
                            R.id.parent_container,
                            BrandFragment::class.java.simpleName,
                            false
                        )
                        loginDialog.dismiss()
                    }
                })
                loginDialog.show(supportFragmentManager, loginDialog.tag)
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

    fun hideNavigation(){
        viewDataBinding.navigation.gone()
    }

    fun visibleNavigation(){
        viewDataBinding.navigation.visible()
    }

}
