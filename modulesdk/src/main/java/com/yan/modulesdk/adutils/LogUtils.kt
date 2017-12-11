package com.yan.modulesdk.adutils

import android.util.Log

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/11 19:31
 *  @description : log工具类
 */
object LogUtils {

    private val DEBUG = true

    fun v(tag: String, msg: String) {
        if (DEBUG) Log.v(tag, checkMsg(msg))
    }

    fun v(tag: String, msg: String, tr: Throwable) {
        if (DEBUG) Log.v(tag, checkMsg(msg), tr)
    }

    fun d(tag: String, msg: String) {
        if (DEBUG) Log.d(tag, checkMsg(msg))
    }

    fun d(tag: String, msg: String, tr: Throwable) {
        if (DEBUG) Log.d(tag, checkMsg(msg), tr)
    }

    fun i(tag: String, msg: String) {
        if (DEBUG) Log.i(tag, checkMsg(msg))
    }

    fun i(tag: String, msg: String, tr: Throwable) {
        if (DEBUG) Log.i(tag, checkMsg(msg), tr)
    }

    fun w(tag: String, msg: String) {
        if (DEBUG) Log.w(tag, checkMsg(msg))
    }

    fun w(tag: String, msg: String, tr: Throwable) {
        if (DEBUG) Log.w(tag, checkMsg(msg), tr)
    }

    fun w(tag: String, tr: Throwable) {
        if (DEBUG) Log.w(tag, tr)
    }

    fun e(tag: String, msg: String?) {
        if (DEBUG) Log.e(tag, checkMsg(msg))
    }

    fun e(tag: String, msg: String, tr: Throwable) {
        if (DEBUG) Log.e(tag, checkMsg(msg), tr)
    }

    private fun checkMsg(msg: String?): String = msg ?: "null"
}
