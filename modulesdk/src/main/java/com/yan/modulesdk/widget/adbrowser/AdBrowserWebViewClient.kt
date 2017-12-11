package com.yan.modulesdk.widget.adbrowser

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.text.TextUtils
import android.webkit.WebView
import android.webkit.WebViewClient

import com.yan.modulesdk.adutils.LogUtils

import java.net.URISyntaxException

/**
 * Custom WebViewClient for AdBrowserWebView which handles different url schemes.
 * Has listener to communicate with buttons on AdBrowserLayout.
 */
class AdBrowserWebViewClient(listener: Listener?) : WebViewClient() {

    private var mListener: Listener? = null

    private val mEmptyListener = object : Listener {
        override fun onPageStarted() {}

        override fun onPageFinished(canGoBack: Boolean) {}

        override fun onReceiveError() {}

        override fun onLeaveApp() {}
    }

    interface Listener {
        fun onPageStarted()

        fun onPageFinished(canGoBack: Boolean)

        fun onReceiveError()

        fun onLeaveApp()
    }

    init {
        if (listener == null) {
            LogUtils.i(LOG_TAG, "Error: Wrong listener")
            mListener = mEmptyListener
        } else {
            mListener = listener
        }
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        var url = url
        LogUtils.i(LOG_TAG, "shouldOverrideUrlLoading url=" + url)
        val context = view.context

        val uri: Uri?
        try {
            uri = Uri.parse(url)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            return false
        }

        if (uri == null) {
            return false
        }

        val scheme = uri.scheme
        val host = uri.host

        if (TextUtils.isEmpty(scheme)) {
            return false
        }

        if (scheme.equals(TEL_SCHEME, ignoreCase = true)) {
            val intent = Intent(Intent.ACTION_DIAL, uri)
            resolveAndStartActivity(intent, context)

        } else if (scheme.equals(MAILTO_SCHEME, ignoreCase = true)) {
            url = url.replaceFirst("mailto:".toRegex(), "")
            url = url.trim { it <= ' ' }
            val intent = Intent(Intent.ACTION_SEND, uri)
            intent.setType(HEADER_PLAIN_TEXT).putExtra(Intent.EXTRA_EMAIL, arrayOf(url))
            resolveAndStartActivity(intent, context)

        } else if (scheme.equals(GEO_SCHEME, ignoreCase = true)) {
            val searchAddress = Intent(Intent.ACTION_VIEW, uri)
            resolveAndStartActivity(searchAddress, context)

        } else if (scheme.equals(YOUTUBE_SCHEME, ignoreCase = true)) {
            leaveApp(url, context)

        } else if (scheme.equals(HTTP_SCHEME, ignoreCase = true) || scheme.equals(HTTPS_SCHEME, ignoreCase = true)) {
            return checkHost(url, host, context)

        } else if (scheme.equals(INTENT_SCHEME, ignoreCase = true)) {
            handleIntentScheme(url, context)
        } else if (scheme.equals(MARKET_SCHEME, ignoreCase = true)) {
            handleMarketScheme(url, context)
        } else {
            return true
        }

        return true
    }

    /**
     * Checks host
     *
     * @param url  - full url
     * @param host - host from url
     * @return true - if param host equals with geo, market or youtube host
     * false - otherwise
     */
    private fun checkHost(url: String, host: String, context: Context): Boolean {
        if (host.equals(GEO_HOST, ignoreCase = true)) {
            val searchAddress = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            resolveAndStartActivity(searchAddress, context)

        } else if (host.equals(MARKET_HOST, ignoreCase = true)
                || host.equals(YOUTUBE_HOST1, ignoreCase = true)
                || host.equals(YOUTUBE_HOST2, ignoreCase = true)) {
            leaveApp(url, context)

        } else {
            return false
        }
        return true
    }

    private fun handleMarketScheme(url: String, context: Context) {
        var url = url
        try {
            val intent = Intent.parseUri(url, 0)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (isActivityResolved(intent, context)) {
                context.startActivity(intent)
            } else {
                val uri = Uri.parse(url)
                val id = uri.getQueryParameter("id")
                url = PLAY_STORE_URL + id
                leaveApp(url, context)
            }
        } catch (e: Exception) {
            mListener?.onReceiveError()
        }

    }

    private fun handleIntentScheme(url: String, context: Context) {
        var url = url
        try {
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if (isActivityResolved(intent, context)) {
                context.startActivity(intent)
            } else {
                url = PLAY_STORE_URL + intent.`package`
                leaveApp(url, context)
            }
        } catch (e: URISyntaxException) {
            mListener?.onReceiveError()
        }

    }

    private fun leaveApp(url: String, context: Context) {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        resolveAndStartActivity(intent, context)
        mListener?.onLeaveApp()
    }

    private fun resolveAndStartActivity(intent: Intent, context: Context) {
        if (isActivityResolved(intent, context)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        } else {
            mListener?.onReceiveError()
        }
    }

    private fun isActivityResolved(intent: Intent, context: Context): Boolean {
        return context.packageManager
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        mListener?.onPageStarted()
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        mListener?.onPageFinished(view.canGoBack())
    }

    override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        val mess = "onReceivedError: " + description
        LogUtils.i(LOG_TAG, mess)
        mListener?.onReceiveError()
    }

    companion object {

        private val LOG_TAG = AdBrowserWebViewClient::class.java.simpleName

        val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id="

        private val HEADER_PLAIN_TEXT = "plain/text"

        private val TEL_SCHEME = "tel"
        private val MAILTO_SCHEME = "mailto"
        private val GEO_SCHEME = "geo"
        private val MARKET_SCHEME = "market"
        private val YOUTUBE_SCHEME = "vnd.youtube"
        private val HTTP_SCHEME = "http"
        private val HTTPS_SCHEME = "https"
        private val INTENT_SCHEME = "intent"

        private val GEO_HOST = "maps.google.com"
        private val MARKET_HOST = "play.google.com"
        private val YOUTUBE_HOST1 = "www.youtube.com"
        private val YOUTUBE_HOST2 = "m.youtube.com"
    }
}
