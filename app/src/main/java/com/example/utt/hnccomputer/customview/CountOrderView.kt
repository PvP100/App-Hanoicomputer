package com.example.utt.hnccomputer.customview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.utt.hnccomputer.base.BaseConstraintCustomView
import com.example.utt.hnccomputer.databinding.ViewCountOrderBinding
import com.example.utt.hnccomputer.extension.toast

class CountOrderView(context: Context, attrs: AttributeSet? = null) :
    BaseConstraintCustomView<ViewCountOrderBinding>(context, attrs) {

    private var count = 1

    private var quantity: Int? = 0

    override fun getCustomViewBinding(context: Context): ViewCountOrderBinding {
        return ViewCountOrderBinding.inflate(LayoutInflater.from(context), this, false)
    }

    var onCountListener: (Int) -> Unit =  {}

    override fun initListener() {
        binding.apply {
            minus.setOnClickListener {
                setCount(count.minus(1))
                count = count.minus(1)
                onCountListener(count)
            }
            plus.setOnClickListener {
                if (count == quantity) {
                    context.toast("Không thể mua quá số lượng hàng còn trong kho")
                } else {
                    setCount(count.plus(1))
                }
                onCountListener(count)
            }
        }
    }

    fun setQuantity(max: Int?) {
        this.quantity = max
    }

    fun setCount(count: Int) {
        this.count = count
        binding.tvCount.text = count.toString()
    }

    fun getCount() = count

    override fun initStyAble(a: TypedArray) {

    }

    override val styleAble: IntArray?
        get() = null
}