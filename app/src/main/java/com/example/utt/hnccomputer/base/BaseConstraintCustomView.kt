package com.example.utt.hnccomputer.base

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewbinding.ViewBinding

abstract class BaseConstraintCustomView<B : ViewBinding>(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private var _binding: B? = null
    val binding get() = _binding!!

    abstract fun getCustomViewBinding(context: Context): B

    abstract fun initListener()

    abstract fun initStyAble()

    abstract val styleAble: IntArray?

    init {
        _binding = getCustomViewBinding(context)
        addView(_binding?.root)

        initStyAble()
        initListener()
    }
}