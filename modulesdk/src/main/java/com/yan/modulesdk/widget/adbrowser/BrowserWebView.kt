package com.yan.modulesdk.widget.adbrowser

import android.content.Context
import android.webkit.WebSettings.PluginState
import android.webkit.WebView

/**
 * @function 自定义WebView，设置一些能用的参数
 */
class BrowserWebView(context: Context) : WebView(context) {

    init {
        init()
    }

    private fun init() {
        val webSettings = settings
        webSettings.javaScriptEnabled = true

        webSettings.pluginState = PluginState.ON

        webSettings.builtInZoomControls = true
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        setInitialScale(1)
    }
}
