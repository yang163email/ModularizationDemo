package com.yan.modulesdk.okhttp.request

import com.yan.modulesdk.RequestParams
import okhttp3.FormBody
import okhttp3.Request

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 14:14
 *  @description : 为Okhttp的request部分
 */
object CommonRequest {

    /**
     * 创建POST的请求request
     */
    fun createPostRequest(url: String, params: RequestParams): Request {
        val formBodyBuilder = FormBody.Builder()
        params.urlParams.forEach {
            formBodyBuilder.add(it.key, it.value)
        }
        val formBody = formBodyBuilder.build()
        return Request.Builder().post(formBody).build()
    }

    /**
     * 创建get请求request
     */
    fun createGetRequest(url: String, params: RequestParams): Request {
        val stringBuilder = StringBuilder(url).append("?")
        params.urlParams.forEach {
            stringBuilder.append(it.key)
                    .append("=")
                    .append(it.value)
                    .append("&")
        }
        return Request.Builder()
                .url(stringBuilder.substring(0, stringBuilder.length - 1))
                .get()
                .build()
    }
}