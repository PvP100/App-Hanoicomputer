package com.example.utt.hnccomputer.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.databinding.ViewHncHeaderBinding
import com.example.utt.hnccomputer.extension.gone
import com.example.utt.hnccomputer.extension.statusBarHeight
import com.example.utt.hnccomputer.extension.visible
import kotlinx.android.synthetic.main.item_cell_brand_home.view.*

class HncHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    interface IOnClickHeader {
        fun onLeftClick()
        fun onRightClick()
    }

    var listener: IOnClickHeader? = null

    companion object {
        const val TYPE_NORMAL = 0
        const val TYPE_SEARCH = 1
    }

    private val binding = ViewHncHeaderBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)

        val lp = binding.headerContainer.layoutParams
        lp.height = resources.getDimensionPixelSize(R.dimen.height_top_bar) + context.statusBarHeight()
        binding.headerContainer.setPadding(0, context.statusBarHeight(),0,0)

        val a = context.obtainStyledAttributes(attrs, R.styleable.HncHeaderView)

        try {
            if (a.hasValue(R.styleable.HncHeaderView_header_type)) {
                when(a.getInt(R.styleable.HncHeaderView_header_type, 0)) {
                    TYPE_NORMAL -> {
                        binding.searchView.gone()
                        val layoutParams = binding.leftIcon.layoutParams as LayoutParams
                        layoutParams.dimensionRatio = "w,1:1"
                        binding.leftIcon.layoutParams = layoutParams
                        binding.headerTitle.apply {
                            if (a.hasValue(R.styleable.HncHeaderView_header_title)) {
                                visible()
                                text = a.getString(R.styleable.HncHeaderView_header_title)
                            }
                        }
                    }
                    TYPE_SEARCH -> {

                    }
                }
            }
            if (a.hasValue(R.styleable.HncHeaderView_left_drawable)) {
                binding.leftIcon.setImageResource(a.getResourceId(R.styleable.HncHeaderView_left_drawable, 0))
            }
            if (a.hasValue(R.styleable.HncHeaderView_right_drawable)) {
                binding.rightIcon.setImageResource(a.getResourceId(R.styleable.HncHeaderView_right_drawable, 0))
            }

            binding.rightIcon.setOnClickListener {
                listener?.onRightClick()
            }
            binding.leftIcon.setOnClickListener {
                listener?.onLeftClick()
            }
        } finally {
            a.recycle()
        }

    }

    fun setHeaderTitle(title: String) {
        binding.headerTitle.text = title
    }

}