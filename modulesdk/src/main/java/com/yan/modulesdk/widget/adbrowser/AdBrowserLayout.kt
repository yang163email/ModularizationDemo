package com.yan.modulesdk.widget.adbrowser

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout

import com.yan.modulesdk.adutils.Utils


/**
 * 广告页面布局
 */
class AdBrowserLayout(context: Context) : RelativeLayout(context) {

    private val mFooterView: RelativeLayout
    val progressBar: ProgressBar
    val backButton: Button
    val refreshButton: Button
    val closeButton: Button
    val nativeButton: Button
    val webView: BrowserWebView

    private val mBase64Drawables = Base64Drawables()

    init {
        val params = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        layoutParams = params

        webView = BrowserWebView(context)
        webView.layoutParams = params
        addView(webView)

        mFooterView = RelativeLayout(context)
        configFooterView()

        val buttonsContainer = LinearLayout(context)
        configButtonsContainer(buttonsContainer)

        val buttonWidth = Utils.getDisplayMetrics(context).widthPixels / 5
        val buttons_params = RelativeLayout.LayoutParams(
                buttonWidth, ViewGroup.LayoutParams.MATCH_PARENT)

        val size = Utils.dip2px(context,
                HEADER_HEIGHT_DP.toFloat()) / 2
        val pb_params = RelativeLayout.LayoutParams(size, size)
        pb_params.addRule(RelativeLayout.CENTER_IN_PARENT)

        progressBar = ProgressBar(context)
        configProgressButton(context, buttonsContainer, buttons_params, pb_params)

        backButton = Button(context)
        configBackButton(context, buttonsContainer, buttons_params, pb_params)

        refreshButton = Button(context)
        configRefreshButton(context, buttonsContainer, buttons_params, pb_params)

        nativeButton = Button(context)
        configNativeButton(context, buttonsContainer, buttons_params, pb_params)

        closeButton = Button(context)
        configCloseButton(context, buttonsContainer, buttons_params, pb_params)

        mFooterView.addView(initBottomWhiteLineView(context))
    }

    private fun configProgressButton(context: Context,
                                     buttonsContainer: LinearLayout,
                                     buttons_params: RelativeLayout.LayoutParams,
                                     pb_params: RelativeLayout.LayoutParams) {
        val progressLayout = RelativeLayout(context)
        progressLayout.layoutParams = buttons_params
        progressBar.layoutParams = pb_params
        progressLayout.addView(progressBar)
        buttonsContainer.addView(progressLayout)
    }

    private fun configButtonsContainer(buttonsContainer: LinearLayout) {
        val buttonsContainerParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        buttonsContainer.layoutParams = buttonsContainerParams
        buttonsContainer.setBackgroundColor(Color.BLACK)
        mFooterView.addView(buttonsContainer)
    }

    private fun configFooterView() {
        val footer_params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                Utils.dip2px(context, HEADER_HEIGHT_DP.toFloat()))
        footer_params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        mFooterView.layoutParams = footer_params
        addView(mFooterView)
    }

    private fun initBottomWhiteLineView(context: Context): View {
        val whiteLine = View(context)
        val whiteLineParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 1)
        whiteLineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        whiteLine.layoutParams = whiteLineParams
        whiteLine.setBackgroundColor(Color.WHITE)
        return whiteLine
    }

    @SuppressLint("NewApi")
    private fun configBackButton(context: Context, buttonsContainer: LinearLayout,
                                 buttons_params: RelativeLayout.LayoutParams,
                                 pb_params: RelativeLayout.LayoutParams) {
        val backLayout = RelativeLayout(context)
        backLayout.layoutParams = buttons_params
        if (Build.VERSION.SDK_INT < 16) {
            backButton.setBackgroundDrawable(Utils.decodeImage(mBase64Drawables.backInactive))
        } else {
            backButton.background = Utils.decodeImage(mBase64Drawables.backInactive)
        }
        backButton.layoutParams = pb_params
        backLayout.addView(backButton)
        buttonsContainer.addView(backLayout)
    }

    @SuppressLint("NewApi")
    private fun configRefreshButton(context: Context, buttonsContainer: LinearLayout,
                                    buttons_params: RelativeLayout.LayoutParams,
                                    pb_params: RelativeLayout.LayoutParams) {
        val refreshLayout = RelativeLayout(context)
        refreshLayout.layoutParams = buttons_params
        if (Build.VERSION.SDK_INT < 16) {
            refreshButton.setBackgroundDrawable(Utils.decodeImage(mBase64Drawables.refresh))
        } else {
            refreshButton.background = Utils.decodeImage(mBase64Drawables.refresh)
        }
        refreshButton.layoutParams = pb_params
        refreshLayout.addView(refreshButton)
        buttonsContainer.addView(refreshLayout)
    }

    @SuppressLint("NewApi")
    private fun configNativeButton(context: Context, buttonsContainer: LinearLayout,
                                   buttons_params: RelativeLayout.LayoutParams,
                                   pb_params: RelativeLayout.LayoutParams) {
        val nativeLayout = RelativeLayout(context)
        nativeLayout.layoutParams = buttons_params
        if (Build.VERSION.SDK_INT < 16) {
            nativeButton.setBackgroundDrawable(Utils.decodeImage(mBase64Drawables.nativeBrowser))
        } else {
            nativeButton.background = Utils.decodeImage(mBase64Drawables.nativeBrowser)
        }
        nativeButton.layoutParams = pb_params
        nativeLayout.addView(nativeButton)
        buttonsContainer.addView(nativeLayout)
    }

    @SuppressLint("NewApi")
    private fun configCloseButton(context: Context, buttonsContainer: LinearLayout,
                                  buttons_params: RelativeLayout.LayoutParams,
                                  pb_params: RelativeLayout.LayoutParams) {
        val closeLayout = RelativeLayout(context)
        closeLayout.layoutParams = buttons_params
        if (Build.VERSION.SDK_INT < 16) {
            closeButton.setBackgroundDrawable(Utils.decodeImage(mBase64Drawables.close))
        } else {
            closeButton.background = Utils.decodeImage(mBase64Drawables.close)
        }
        closeButton.layoutParams = pb_params
        closeLayout.addView(closeButton)
        buttonsContainer.addView(closeLayout)
    }

    companion object {

        private val HEADER_HEIGHT_DP = 45
    }
}
