package com.example.attractions.fragment.webview

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import com.example.attractions.databinding.FragmentWebviewBinding

class WebviewFragment : Fragment() {

    lateinit var binding: FragmentWebviewBinding
    lateinit var webUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebviewBinding.inflate(inflater, container, false)
        arguments?.apply {
            webUrl = this.get("weburl") as String
        }


        val webView = binding.webView
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        webView.loadUrl(webUrl)
        return binding.root
    }


}