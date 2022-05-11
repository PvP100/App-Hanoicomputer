package com.example.utt.hnccomputer.ui.fragment.splash

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.ui.activity.main.MainActivity
import com.example.utt.hnccomputer.base.BaseFragment
import com.example.utt.hnccomputer.databinding.FragmentSplashBinding
import com.example.utt.hnccomputer.extension.openActivityWithoutAddtoBackstack
import com.example.utt.hnccomputer.ui.fragment.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun initListener() {
        Looper.getMainLooper()?.let {
            Handler(it).postDelayed({
                replaceFragment(MainFragment(), R.id.parent_container)
            }, 3000)
        }
    }

    private val viewModel: SplashViewModel by viewModels()
    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        binding: FragmentSplashBinding
    ) {
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun initData() {
        with(viewModel) {

        }
    }
}
