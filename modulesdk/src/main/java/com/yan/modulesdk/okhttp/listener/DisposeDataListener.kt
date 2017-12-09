package com.yan.modulesdk.okhttp.listener

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 15:42
 *  @description : 业务逻辑层真正处理的地方，包括java层异常和业务层异常
 */
interface DisposeDataListener {

    /**
     * 请求成功回调处理
     */
    fun onSuccess(any: Any)

    /**
     * 请求失败回调
     */
    fun onFailed(any: Any)
}