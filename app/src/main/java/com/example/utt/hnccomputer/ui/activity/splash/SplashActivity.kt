package com.example.utt.hnccomputer.ui.activity.splash

import androidx.activity.viewModels
import com.example.utt.hnccomputer.base.BaseDataBindingActivity
import com.example.utt.hnccomputer.extension.enableFullScreen
import com.example.utt.hnccomputer.ui.fragment.splash.SplashFragment
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseDataBindingActivity<ActivitySplashBinding, SplashActivityViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_splash
    override val viewModel: SplashActivityViewModel by viewModels()

    override fun initView() {
        enableFullScreen()
        transition(
            SplashFragment(),
            R.id.container,
            SplashFragment::class.java.simpleName,
            false
        )
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
    }
}