package com.example.utt.hnccomputer.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.ProgressBar
import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieAnimationView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.entity.*
import com.example.utt.hnccomputer.extension.*
import com.example.utt.hnccomputer.ui.dialog.ErrorResponseDialog
import com.example.utt.hnccomputer.utils.Define
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private val TAG = this::class.java.name
    private var _binding: B? = null
    val binding get() = _binding!!
    var bannerFlag: Boolean = false
    private var offset = 0F
    private var offsetRcv = 0
    var isExecuting = false

    val errorDialog = ErrorResponseDialog()

    protected var mSavedInstanceState: Bundle? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getFragmentBinding(inflater, container)
        mSavedInstanceState = savedInstanceState
        initView(inflater, container, binding)
        initListener()
        initData()
        return binding.root
    }

    abstract fun initView(inflater: LayoutInflater, container: ViewGroup?, binding: B)

    abstract fun initData()

    abstract fun initListener()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    protected fun transitFragment(
        fragment: BaseFragment<*>,
        @IdRes id: Int,
        args: Bundle? = null,
        @AnimRes enterAnim: Int = R.anim.slide_in,
        @AnimRes exitAnim: Int = R.anim.fade_out,
        @AnimRes popEnter: Int = R.anim.fade_in,
        @AnimRes popExit: Int = R.anim.slide_out,
    ) {
        val fragmentManager = activity?.supportFragmentManager
        args?.let {
            fragment.arguments = args
        }
        fragmentManager?.beginTransaction()?.setCustomAnimations(
            enterAnim,  // enter
            exitAnim,  // exit
            popEnter,   // popEnter
            popExit  // popExit
        )?.add(id, fragment, fragment.javaClass.name)?.addToBackStack(fragment.TAG)?.commit()
    }

    protected fun replaceFragment(
        fragment: BaseFragment<*>,
        @IdRes id: Int,
        args: Bundle? = null,
        animation: Boolean = true
    ) {
        val fragmentManager = activity?.supportFragmentManager
        args?.let {
            fragment.arguments = args
        }
        if (animation) {
            fragmentManager?.beginTransaction()?.setCustomAnimations(
                R.anim.slide_in,  // enter
                R.anim.fade_out,  // exit
                R.anim.fade_in,   // popEnter
                R.anim.slide_out  // popExit
            )?.replace(id, fragment, fragment.javaClass.name)?.commit()
        } else {
            fragmentManager?.beginTransaction()?.replace(id, fragment, fragment.javaClass.name)
                ?.commit()
        }
    }

    protected fun transitChildFragment(
        parentFragment: Fragment,
        fragment: BaseFragment<*>,
        @IdRes id: Int,
        args: Bundle? = null
    ) {
        val fragmentManager = parentFragment.childFragmentManager
        fragment.arguments = args

        fragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in,  // enter
            R.anim.fade_out,  // exit
            R.anim.fade_in,   // popEnter
            R.anim.slide_out  // popExit
        ).add(id, fragment, fragment.javaClass.name).addToBackStack(fragment.TAG).commit()
    }

    fun transitFragment(
        fragment: BaseFragment<*>,
        @IdRes id: Int,
        isAddToBackStack: Boolean = true,
        args: Bundle? = null
    ) {
        val fragmentManager = activity?.supportFragmentManager
        fragment.arguments = args
        fragmentManager?.beginTransaction()?.setCustomAnimations(
            R.anim.slide_in,  // enter
            R.anim.fade_out,  // exit
            R.anim.fade_in,   // popEnter
            R.anim.slide_out  // popExit
        )?.add(id, fragment, fragment.javaClass.name)?.addToBackStack(fragment.TAG)?.commit()
    }

    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun showAnimationProgress(progressBar: LottieAnimationView) {
        progressBar.visible()
        progressBar.setAnimation(R.raw.loading_animation)
        progressBar.playAnimation()
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    fun hideAnimationProgress(progressBar: LottieAnimationView) {
        progressBar.pauseAnimation()
        progressBar.gone()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setHeaderAnimation(
        appBar: AppBarLayout? = null,
        recyclerView: RecyclerView? = null,
        parentAppBar: AppBarLayout,
        refreshLayout: SwipeRefreshLayout
    ) {

        if (parentFragment != null) {
            appBar?.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
                if (abs(offset - appBarLayout.y) > 120) {
                    scrollAppbar(
                        offset > appBarLayout.y,
                        parentAppBar
                    )
                    offset = appBarLayout.y
                }
            })

            refreshLayout.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        parentAppBar.setExpanded(true)
                    }
                }
                return@setOnTouchListener false
            }

            recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (abs(offsetRcv - recyclerView.computeVerticalScrollOffset()) > 50) {
                        scrollAppbar(
                            offsetRcv < recyclerView.computeVerticalScrollOffset(),
                            parentAppBar
                        )
                        offsetRcv = recyclerView.computeVerticalScrollOffset()
                    }
                }
            })
        }
    }

    fun scrollAppbar(isScrolledDown: Boolean, appBar: AppBarLayout?) {
        if (!isExecuting) {
            appBar?.setExpanded(!isScrolledDown)
            isExecuting = true
            Looper.myLooper()?.let {
                Handler(it).postDelayed({
                    isExecuting = false
                }, 300)
            }
        }
    }

    open fun <U> handleObjResponse(
        data: BaseObjectResponse<U>, progressBar: LottieAnimationView
    ) {
        when (data.type) {
            Define.ResponseStatus.LOADING -> {
                showAnimationProgress(progressBar)
            }
            Define.ResponseStatus.SUCCESS -> {
                hideAnimationProgress(progressBar)
                getObjectResponse(data.data)
            }
            Define.ResponseStatus.ERROR -> {
                hideAnimationProgress(progressBar)
            }
        }
    }
    open fun handleNoDataResponse(
        data: BaseResponse, progressBar: LottieAnimationView, listener: () -> Unit = {}
    ) {
        when (data.typeBase) {
            Define.ResponseStatus.LOADING -> {
                if (!data.isRefreshNoResponse) {
                    showAnimationProgress(progressBar)
                }
            }
            Define.ResponseStatus.SUCCESS -> {
                listener()
                hideAnimationProgress(progressBar)
            }
            Define.ResponseStatus.ERROR -> {
                if (data.errorNoResponse is BaseError) {
                    handleValidateError(data.errorNoResponse)
                } else {
                    with(data.errorNoResponse?.getErrorBody()) {
                        handleValidateError(this?.msg?.let { BaseError(it) })
                    }
                }
                hideAnimationProgress(progressBar)
            }
        }
    }


    open fun <U> handleListResponse(
        data: BaseListResponse<U>, progressBar: LottieAnimationView
    ) {
        when (data.type) {
            Define.ResponseStatus.LOADING -> {
                showAnimationProgress(progressBar)
            }
            Define.ResponseStatus.SUCCESS -> {
                hideAnimationProgress(progressBar)
                getListResponse(data.data)
            }
            Define.ResponseStatus.ERROR -> {
                hideAnimationProgress(progressBar)
            }
        }
    }

    open fun <U> handleObjectLoadMoreResponse(
        data: BaseObjectLoadMoreResponse<U>, progressBar: LottieAnimationView, errorCallBack: (status: Int?, msg: String?) -> Unit = { _, _ -> }
    ) {
        when (data.type) {
            Define.ResponseStatus.LOADING -> {
                if (!data.isRefresh && !data.isLoadmore) {
                    showAnimationProgress(progressBar)
                }
            }
            Define.ResponseStatus.SUCCESS -> {
                hideAnimationProgress(progressBar)
                getListLoadMoreResponse(data.data, data.isRefresh, data.isLoadmore)
            }
            Define.ResponseStatus.ERROR -> {
                hideAnimationProgress(progressBar)
                with(data.error?.getErrorBody()) {
                    errorCallBack(this?.status, this?.msg)
                }
            }
        }
    }


    open fun <U> getObjectResponse(
        data: U
    ) {
    }

    open fun <U> getListResponse(
        data: List<U>?
    ) {
    }

    open fun <U> getListLoadMoreResponse(
        data: U?, isRefresh: Boolean, canLoadMore: Boolean
    ) {
    }

    protected open fun handleValidateError(throwable: BaseError?) {}
}