package com.yan.modularization.net.http

import com.yan.modularization.module.recommend.BaseRecommendModel
import com.yan.modulesdk.okhttp.CommonOkHttpClient
import com.yan.modulesdk.okhttp.https.HttpConstants
import com.yan.modulesdk.okhttp.listener.DisposeDataHandle
import com.yan.modulesdk.okhttp.listener.DisposeDataListener
import com.yan.modulesdk.okhttp.request.CommonRequest
import com.yan.modulesdk.okhttp.request.RequestParams

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 20:56
 *  @description : 存放应用中所有的请求
 */
object RequestCenter {

    /**
     * 根据参数发送请求
     */
    private fun postRequest(url: String, params: RequestParams?,
                            listener: DisposeDataListener, clazz: Class<*>) {
        CommonOkHttpClient.sendRequest(CommonRequest.createGetRequest(url, params),
                DisposeDataHandle(listener, clazz))
    }

    /**
     * 发送首页请求
     */
    fun requestRecommendData(listener: DisposeDataListener) {
        postRequest(HttpConstants.HOME_RECOMMEND, null,
                listener, BaseRecommendModel::class.java)
    }
}