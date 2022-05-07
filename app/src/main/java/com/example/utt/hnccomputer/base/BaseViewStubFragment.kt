package com.example.utt.hnccomputer.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.example.utt.hnccomputer.databinding.StubFragmentBinding

abstract class BaseViewStubFragment<T: ViewBinding> : BaseFragment<T>() {

    private lateinit var viewStubBinding: StubFragmentBinding

    private var hasInflated = false
    private var visible = false


    override fun initView(inflater: LayoutInflater, container: ViewGroup?, binding: T) {
        viewStubBinding = StubFragmentBinding.inflate(inflater, container, false)
        viewStubBinding.fragmentViewStub.viewStub?.layoutResource = getViewStubLayoutResource()
        if (visible && !hasInflated) {
            val inflatedView = viewStubBinding.fragmentViewStub.viewStub?.inflate()
            inflatedView?.let { onCreateViewAfterViewStubInflated(it, mSavedInstanceState) }
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
        if (viewStubBinding.fragmentViewStub.viewStub != null && !hasInflated) {
            val inflatedView = viewStubBinding.fragmentViewStub.viewStub!!.inflate()
            onCreateViewAfterViewStubInflated(inflatedView!!, mSavedInstanceState)
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