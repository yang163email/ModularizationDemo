package com.yan.modulesdk.okhttp.listener

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 15:45
 *  @description : 处理数据
 */
open class DisposeDataHandle {
    var mListener: DisposeDataListener? = null
    var mClass: Class<*>? = null
    var mSource: String? = null

    constructor(listener: DisposeDataListener) {
        this.mListener = listener
    }

    constructor(listener: DisposeDataListener, clazz: Class<*>) {
        this.mListener = listener
        this.mClass = clazz
    }

    constructor(listener: DisposeDataListener, source: String) {
        this.mListener = listener
        this.mSource = source
    }
}