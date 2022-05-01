package com.example.webview

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupWebView()
    }

    private fun setupWebView() {
        // Configure related browser settings
        webView!!.settings.javaScriptEnabled = true
        webView!!.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        // Configure the client to use when opening URLs
        webView!!.webViewClient = object : WebViewClient() {
            var progressDialog: ProgressDialog? = ProgressDialog(this@MainActivity)
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
                progressDialog!!.setTitle("Loading...")
                progressDialog!!.setMessage("Please wait...")
                progressDialog!!.setCancelable(false)
                progressDialog!!.show()
            }

            override fun onPageCommitVisible(view: WebView, url: String) {
                super.onPageCommitVisible(view, url)
                if (progressDialog != null) {
                    progressDialog!!.dismiss()
                }
            }
        }

        // Enable responsive layout
        webView!!.settings.useWideViewPort = true

        // Zoom out if the content width is greater than the width of the viewport
        webView!!.settings.loadWithOverviewMode = true
        webView!!.settings.setSupportZoom(true)
        webView!!.settings.builtInZoomControls = true // allow pinch to zooom
        webView!!.settings.displayZoomControls =
            true // disable the default zoom controls on the page
        webView!!.loadUrl("https://www.google.com/")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}