package com.yan.modulesdk.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.widget.Button
import com.yan.modulesdk.adutils.LogUtils
import com.yan.modulesdk.adutils.Utils
import com.yan.modulesdk.widget.adbrowser.AdBrowserLayout
import com.yan.modulesdk.widget.adbrowser.AdBrowserWebViewClient
import com.yan.modulesdk.widget.adbrowser.Base64Drawables
import com.yan.modulesdk.widget.adbrowser.BrowserWebView

/**
 * @author: qndroid
 * @function: 广告WebView页面
 * @date: 16/7/5
 */
class AdBrowserActivity : Activity() {

    private var mAdBrowserWebview: BrowserWebView? = null
    private lateinit var mLayout: AdBrowserLayout

    private var mIsBackFromMarket = false

    private var mProgress: View? = null
    private var mBackButton: Button? = null

    private var mUrl: String? = null

    private var mWebClientListener: AdBrowserWebViewClient.Listener? = null

    private val mBase64Drawables = Base64Drawables()

    private val isValidExtras: Boolean
        get() {
            mUrl = intent.getStringExtra(KEY_URL)
            return !TextUtils.isEmpty(mUrl)
        }

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)

        if (isValidExtras) {

            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN)

            mLayout = AdBrowserLayout(applicationContext)
            setContentView(mLayout)

            mProgress = mLayout.progressBar
            mBackButton = mLayout.backButton

            mAdBrowserWebview = mLayout.webView
            initWebView(mAdBrowserWebview!!)

            if (bundle != null) {
                mAdBrowserWebview!!.restoreState(bundle)
            } else {
                mAdBrowserWebview!!.loadUrl(mUrl)
            }
            initButtonListeners(mAdBrowserWebview!!)
        } else {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        LogUtils.i(LOG_TAG, "onPause")
        if (mAdBrowserWebview != null) {
            mAdBrowserWebview!!.onPause()
        }
    }

    override fun onDestroy() {
        LogUtils.i(LOG_TAG, " onDestroy")
        if (mAdBrowserWebview != null) {
            mAdBrowserWebview!!.clearCache(true)
        }
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        LogUtils.i(LOG_TAG, "onResume")
        if (mIsBackFromMarket) {
            //finish();
        }
        mIsBackFromMarket = true
        mLayout.progressBar.visibility = View.INVISIBLE
    }

    private fun initWebView(webView: BrowserWebView) {
        mWebClientListener = initAdBrowserClientListener()
        val client = AdBrowserWebViewClient(mWebClientListener)
        webView.webViewClient = client
        webView.settings.builtInZoomControls = false
    }

    private fun initAdBrowserClientListener(): AdBrowserWebViewClient.Listener {
        return object : AdBrowserWebViewClient.Listener {

            override fun onReceiveError() {
                finish()
            }

            override fun onPageStarted() {
                mProgress!!.visibility = View.VISIBLE
            }

            @SuppressLint("NewApi")
            override fun onPageFinished(canGoBack: Boolean) {
                mProgress!!.visibility = View.INVISIBLE
                if (canGoBack) {
                    setImage(mBackButton, mBase64Drawables.backActive)
                } else {
                    setImage(mBackButton, mBase64Drawables.backInactive)
                }
            }

            override fun onLeaveApp() {

            }
        }
    }

    @SuppressLint("NewApi")
    private fun setImage(button: Button?, imageString: String) {
        if (Build.VERSION.SDK_INT < 16) {
            button!!.setBackgroundDrawable(Utils.decodeImage(imageString))
        } else {
            button!!.background = Utils.decodeImage(imageString)
        }
    }

    private fun initButtonListeners(webView: WebView) {

        mLayout.backButton.setOnClickListener {
            if (webView.canGoBack()) {
                mLayout.progressBar.visibility = View.VISIBLE
                webView.goBack()
            }
        }

        mLayout.closeButton.setOnClickListener { finish() }

        mLayout.refreshButton.setOnClickListener {
            mLayout.progressBar.visibility = View.VISIBLE
            webView.reload()
        }

        mLayout.nativeButton.setOnClickListener {
            val uriString = webView.url
            if (uriString != null) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))

                val isActivityResolved = if (packageManager
                        .resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY) != null)
                    true
                else
                    false
                if (isActivityResolved) {
                    startActivity(browserIntent)
                }
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mAdBrowserWebview!!.canGoBack()) {
                mAdBrowserWebview!!.goBack()
                return true
            }
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        mIsBackFromMarket = false
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mAdBrowserWebview!!.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    companion object {

        private val LOG_TAG = AdBrowserActivity::class.java.simpleName

        val KEY_URL = "url"
    }
}
