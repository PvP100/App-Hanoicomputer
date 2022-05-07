package com.example.utt.hnccomputer.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.doAfterTextChanged
import com.example.utt.hnccomputer.R
import com.example.utt.hnccomputer.databinding.ViewHncSearchBinding
import com.example.utt.hnccomputer.extension.hideKeyboard

class HncSearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    interface IOnSearchListener {
        fun onSearch(value: String)
    }

    var listener: IOnSearchListener? = null

    private var binding: ViewHncSearchBinding =
        ViewHncSearchBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        addView(binding.root)

        val a = context.obtainStyledAttributes(attrs, R.styleable.HncSearchView)

        try {
            if (a.hasValue(R.styleable.HncSearchView_search_view_background)) {
                val background = a.getDrawable(R.styleable.HncSearchView_search_view_background)
                background?.let {
                    binding.edtSearchField.background = it
                }
            }
            if(a.hasValue(R.styleable.HncSearchView_search_view_padding)){
                val background = a.getDimension(R.styleable.HncSearchView_search_view_padding, 0f)
                background.let {
                    binding.edtSearchField.setPadding(it.toInt(), it.toInt(), it.toInt(), it.toInt())
                }
            }
            val hint = a.getString(R.styleable.HncSearchView_search_view_hint)
            hint?.let {
                binding.edtSearchField.hint = it
            }
        } finally {
            a.recycle()
        }
        binding.edtSearchField.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(textView)
            }
            true
        }

        binding.edtSearchField.doAfterTextChanged {
            if (binding.edtSearchField.text.isNullOrEmpty()) {
                binding.viewClearSearch.visibility = View.GONE
            } else {
                binding.viewClearSearch.visibility = View.VISIBLE
            }
        }

        binding.viewClearSearch.setOnClickListener {
            binding.edtSearchField.text = null
        }

    }

    private fun performSearch(textView: TextView) {
        textView.text?.toString()?.let { it1 -> listener?.onSearch(it1) }
        binding.edtSearchField.hideKeyboard()
    }

    fun getText(): String{
        return binding.edtSearchField.text.toString()
    }

}