package com.yan.modularization.utils

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.yan.modularization.module.recommend.RecommendBodyValue
import java.util.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/10 13:21
 *  @description : Util
 */
object Util {

    fun getVersionCode(context: Context): Int {
        var versionCode = 1
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionCode = pi.versionCode
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionCode
    }

    fun getVersionName(context: Context): String {
        var versionName = "1.0.0"
        try {
            val pm = context.packageManager
            val pi = pm.getPackageInfo(context.packageName, 0)
            versionName = pi.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return versionName
    }

    fun createQQUrl(qq: String): Uri {
        val result = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq
        return Uri.parse(result)
    }

    //为ViewPager结构化数据
    fun handleData(value: RecommendBodyValue): ArrayList<RecommendBodyValue> {
        val values = ArrayList<RecommendBodyValue>()
        val titles = value.title!!.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val infos = value.info!!.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val prices = value.price!!.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val texts = value.text!!.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val urls = value.url
        var start = 0
        for (i in titles.indices) {
            val tempValue = RecommendBodyValue()
            tempValue.title = titles[i]
            tempValue.info = infos[i]
            tempValue.price = prices[i]
            tempValue.text = texts[i]
            tempValue.url = extractData(urls, start, 3)
            start += 3

            values.add(tempValue)
        }
        return values
    }

    private fun extractData(source: ArrayList<String>?, start: Int, interval: Int): ArrayList<String> {
        val tempUrls = ArrayList<String>()
        for (i in start until start + interval) {
            tempUrls.add(source!![i])
        }
        return tempUrls
    }

    /**
     * 显示系统软件盘
     * 传入的View必须是EditText及其子类才可以强制显示出
     */
    fun showSoftInputMethod(context: Context, v: View) {
        /* 隐藏软键盘 */
        val inputMethodManager = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(v, InputMethodManager.SHOW_FORCED)
    }

    fun hideSoftInputMethod(context: Context, v: View) {
        /* 隐藏软键盘 */
        val inputMethodManager = context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun hasError() {
        val error = "error"
        Log.e("Util", error)
    }
}
