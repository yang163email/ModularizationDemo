package com.yan.modulesdk.okhttp

import com.yan.modulesdk.okhttp.https.HttpsUtils
import com.yan.modulesdk.okhttp.listener.DisposeDataHandle
import com.yan.modulesdk.okhttp.response.CommonJsonCallback
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 14:30
 *  @description : 请求的发送，请求参数的配置，https的支持
 */
object CommonOkHttpClient {
    /** 超时时间 */
    private const val TIME_OUT = 30L

    val mOkHttpClient: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        //设置超时时间
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS)
        builder.writeTimeout(TIME_OUT, TimeUnit.SECONDS)

        builder.followRedirects(true)
        //https的支持
        builder.hostnameVerifier { hostname, session ->
            true
        }
        builder.sslSocketFactory(HttpsUtils.initSSLSocketFactory(), HttpsUtils.initTrustManager())
        //生成client
        mOkHttpClient = builder.build()
    }

    /**
     * 发送具体的http/https请求
     */
    fun sendRequest(request: Request, handle: DisposeDataHandle): Call {
        val call = mOkHttpClient.newCall(request)
        call.enqueue(CommonJsonCallback(handle))
        return call
    }
}