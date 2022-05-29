package com.example.utt.hnccomputer.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.base.BaseConstraintCustomView
import com.example.utt.hnccomputer.databinding.ViewFilterPriceBinding

class FilterPriceView(context: Context, attrs: AttributeSet? = null) :
    BaseConstraintCustomView<ViewFilterPriceBinding>(context, attrs) {

    var onReturnSelected: (Int) -> Unit = {}

    private var selectedPosition = 0

    override fun getCustomViewBinding(context: Context): ViewFilterPriceBinding {
        return ViewFilterPriceBinding.inflate(LayoutInflater.from(context), this, false)
    }

    override fun initListener() {
        binding.apply {
            btnSale.setOnClickListener {
                selectedPosition = if (selectedPosition == 1) {
                    setSelect(false, it as AppCompatTextView)
                    0
                } else {
                    setSelect(true, it as AppCompatTextView)
                    1
                }
                setSelect(false, btnAsc)
                setSelect(false, btnDesc)
                onReturnSelected(selectedPosition)
            }
            btnAsc.setOnClickListener {
                selectedPosition = if (selectedPosition == 2) {
                    setSelect(false, it as AppCompatTextView)
                    0
                } else {
                    setSelect(true, it as AppCompatTextView)
                    2
                }
                setSelect(false, btnSale)
                setSelect(false, btnDesc)
                onReturnSelected(selectedPosition)
            }
            btnDesc.setOnClickListener {
                selectedPosition = if (selectedPosition == 3) {
                    setSelect(false, it as AppCompatTextView)
                    0
                } else {
                    setSelect(true, it as AppCompatTextView)
                    3
                }
                setSelect(false, btnSale)
                setSelect(false, btnAsc)
                onReturnSelected(selectedPosition)
            }
        }
    }


    private fun setSelect(isSelect: Boolean, tv: AppCompatTextView) {
        tv.setBackgroundResource(if (isSelect) R.drawable.bg_selected_price else R.drawable.bg_unselected_price)
        tv.setTextColor(if (isSelect) context.resources.getColor(R.color.colorPrimary) else context.resources.getColor(R.color.base_10))
    }

    override fun initStyAble(a: TypedArray) {

    }

    override val styleAble: IntArray?
        get() = null
}