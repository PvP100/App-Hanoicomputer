package com.example.utt.hnccomputer.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import kotlinx.android.synthetic.main.stub_fragment.*

abstract class BaseViewStubFragment<T: ViewBinding> : BaseFragment<T>() {

    private var hasInflated = false
    private var visible = false


    override fun initView(inflater: LayoutInflater, container: ViewGroup?, binding: T) {
        fragmentViewStub!!.layoutResource = getViewStubLayoutResource()
        if (visible && !hasInflated) {
            val inflatedView = fragmentViewStub!!.inflate()
            onCreateViewAfterViewStubInflated(inflatedView, mSavedInstanceState)
            afterViewStubInflated(view)
        }
    }

    override fun initData() {

    }
    protected abstract fun onCreateViewAfterViewStubInflated(
        inflatedView: View,
        savedInstanceState: Bundle?
    )


    @LayoutRes
    protected abstract fun getViewStubLayoutResource(): Int

    @CallSuper
    protected fun afterViewStubInflated(originalViewContainerWithViewStub: View?) {
        hasInflated = true
    }

    override fun onResume() {
        super.onResume()
        visible = true
        if (fragmentViewStub != null && !hasInflated) {
            val inflatedView = fragmentViewStub!!.inflate()
            onCreateViewAfterViewStubInflated(inflatedView, mSavedInstanceState)
            afterViewStubInflated(view)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hasInflated = false
    }

    override fun onPause() {
        super.onPause()
        visible = false
    }

    override fun onDetach() {
        super.onDetach()
        hasInflated = false
    }
}