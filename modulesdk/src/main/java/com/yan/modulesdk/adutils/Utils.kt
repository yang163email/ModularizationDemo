package com.yan.modulesdk.adutils

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Base64
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.yan.modulesdk.constant.SDKConstant
import com.yan.modulesdk.constant.SDKConstant.AutoPlaySetting
import com.yan.modulesdk.module.AdValue
import java.io.ByteArrayInputStream
import java.util.*

/**
 * @author qndroid
 */
object Utils {

    /**
     * 获取view的屏幕属性
     *
     * @return
     */
    val VIEW_INFO_EXTRA = "view_into_extra"
    val PROPNAME_SCREENLOCATION_LEFT = "propname_sreenlocation_left"
    val PROPNAME_SCREENLOCATION_TOP = "propname_sreenlocation_top"
    val PROPNAME_WIDTH = "propname_width"
    val PROPNAME_HEIGHT = "propname_height"

    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale).toInt()
    }

    fun getVisiblePercent(pView: View?): Int {
        if (pView != null && pView.isShown) {
            val displayMetrics = pView.context.resources.displayMetrics
            val displayWidth = displayMetrics.widthPixels
            val rect = Rect()
            pView.getGlobalVisibleRect(rect)
            if (rect.top > 0 && rect.left < displayWidth) {
                val areaVisible = (rect.width() * rect.height()).toDouble()
                val areaTotal = (pView.width * pView.height).toDouble()
                return (areaVisible / areaTotal * 100).toInt()
            } else {
                return -1
            }
        }
        return -1
    }

    //is wifi connected
    fun isWifiConnected(context: Context): Boolean {
        if (context.checkCallingOrSelfPermission(permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        return if (info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI) {
            true
        } else false
    }

    //decide can autoplay the ad
    fun canAutoPlay(context: Context, setting: AutoPlaySetting): Boolean {
        var result = true
        when (setting) {
            SDKConstant.AutoPlaySetting.AUTO_PLAY_3G_4G_WIFI -> result = true
            SDKConstant.AutoPlaySetting.AUTO_PLAY_ONLY_WIFI -> if (isWifiConnected(context)) {
                result = true
            } else {
                result = false
            }
            SDKConstant.AutoPlaySetting.AUTO_PLAY_NEVER -> result = false
        }
        return result
    }

    /**
     * 获取对应应用的版本号
     *
     * @param context
     * @return
     */
    fun getAppVersion(context: Context): String {
        var version = "1.0.0" //默认1.0.0版本
        val manager = context.packageManager
        try {
            val info = manager.getPackageInfo(context.packageName, 0)
            version = info.versionName
        } catch (e: Exception) {
        }

        return version
    }

    /**
     * 将数组中的所有素材IE拼接起来，空则拼接“”
     *
     * @param values
     * @return
     */
    fun getAdIE(values: ArrayList<AdValue>?): String {
        val result = StringBuilder()
        if (values != null && values.size > 0) {
            for ((_, adid) in values) {
                result.append(if (adid == "") "" else adid).append(",")
            }
            return result.substring(0, result.length - 1)
        }
        return ""
    }

    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager ?: return displayMetrics
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    fun decodeImage(base64drawable: String): BitmapDrawable {
        val rawImageData = Base64.decode(base64drawable, 0)
        return BitmapDrawable(null, ByteArrayInputStream(rawImageData))
    }

    fun isPad(context: Context): Boolean {

        //如果能打电话那可能是平板或手机，再根据配置判断
        return if (canTelephone(context)) {
            //能打电话可能是手机也可能是平板
            context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
        } else {
            true //不能打电话一定是平板
        }
    }

    private fun canTelephone(context: Context): Boolean {
        val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (telephony.phoneType == TelephonyManager.PHONE_TYPE_NONE) false else true
    }

    fun containString(source: String, destation: String): Boolean {

        if (source == "" || destation == "") {
            return false
        }

        return if (source.contains(destation)) {
            true
        } else false
    }

    fun getViewProperty(view: View): Bundle {
        val bundle = Bundle()
        val screenLocation = IntArray(2)
        view.getLocationOnScreen(screenLocation) //获取view在整个屏幕中的位置
        bundle.putInt(PROPNAME_SCREENLOCATION_LEFT, screenLocation[0])
        bundle.putInt(PROPNAME_SCREENLOCATION_TOP, screenLocation[1])
        bundle.putInt(PROPNAME_WIDTH, view.width)
        bundle.putInt(PROPNAME_HEIGHT, view.height)

        Log.e("Utils", "Left: " + screenLocation[0] + " Top: " + screenLocation[1]
                + " Width: " + view.width + " Height: " + view.height)
        return bundle
    }
}
