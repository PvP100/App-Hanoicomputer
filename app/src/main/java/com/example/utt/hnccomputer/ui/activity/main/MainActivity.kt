package com.example.utt.hnccomputer.ui.activity.main

import androidx.activity.viewModels
import com.example.utt.hnccomputer.base.BaseDataBindingActivity
import com.example.utt.hnccomputer.extension.enableFullScreen
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.permission.PermissionHelper
import com.example.utt.hnccomputer.databinding.ActivityMainBinding
import com.example.utt.hnccomputer.ui.fragment.splash.SplashFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseDataBindingActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.activity_main

    val permissionHelper: PermissionHelper by lazy {
        PermissionHelper()
    }

    override val viewModel: MainViewModel by viewModels()

    override fun initView() {
        enableFullScreen()
        transition(
            SplashFragment(),
            R.id.parent_container,
            SplashFragment::class.java.simpleName,
            isAddToBackStack = false,
            animation = true
        )
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

//    fun hideNavigation(){
//        viewDataBinding.navigation.gone()
//    }
//
//    fun visibleNavigation(){
//        viewDataBinding.navigation.visible()
//    }

}
