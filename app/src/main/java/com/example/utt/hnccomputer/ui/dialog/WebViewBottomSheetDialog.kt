package com.example.utt.hnccomputer.ui.dialog

import android.annotation.TargetApi
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.utt.hnccomputer.databinding.DialogWebviewBottomSheetBinding
import com.example.utt.hnccomputer.utils.BundleKey
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class WebViewBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogWebviewBottomSheetBinding

    companion object {
        fun newInstance(url: String): WebViewBottomSheetDialog {
            val dialog = WebViewBottomSheetDialog()
            dialog.arguments = Bundle().apply {
                putString(BundleKey.KEY_WEB_URL, url)
            }
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setOnKeyListener { p0, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    // Back key is pressed
                    if (event.action != KeyEvent.ACTION_DOWN)
                        true
                    else {
                        when {
                            binding.webView.canGoBack() -> {
                                binding.webView.goBack()
                            }
                            else -> {
                                dialog?.dismiss() // Optional
                                true
                            }
                        }
                    }
                }
                false
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogWebviewBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startInitialization()
    }

    private fun startInitialization() {
        if (isAdded) {
            binding.apply {
                val url = requireArguments().getString(BundleKey.KEY_WEB_URL) ?: ""
                if (!url.isNullOrEmpty()) {
//                    mContentViewBinding.loading = true

                    webView.settings.javaScriptEnabled = true
                    webView.settings.javaScriptCanOpenWindowsAutomatically = true
                    webView.webChromeClient = WebChromeClient()

                    webView.webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

                            view.loadUrl(url)
//                            mContentViewBinding.loading = true
                            //  CustomTabsHelper.openWebView(requireContext(), url)
                            return true
                        }


                        @TargetApi(Build.VERSION_CODES.N)
                        override fun shouldOverrideUrlLoading(
                            view: WebView,
                            request: WebResourceRequest
                        ): Boolean {
                            view.loadUrl(request.url.toString())
                            return true
                        }


                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)

//                            mContentViewBinding.loading = false
                        }

                    }

                    webView.webChromeClient = WebChromeClient()

                    webView.loadUrl(url)

                }
            }
        }
    }
}