package com.yan.modulesdk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yan.modulesdk.okhttp.listener.DisposeDataHandle
import com.yan.modulesdk.okhttp.listener.DisposeDataListener
import com.yan.modulesdk.okhttp.request.CommonRequest
import com.yan.modulesdk.okhttp.response.CommonJsonCallback

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 15:10
 *  @description : TODO
 */
class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun test() {
        CommonOkHttpClient.sendRequest(
                CommonRequest.createGetRequest("www.baidu.com"),
                object : CommonJsonCallback(
                        object : DisposeDataHandle(
                                object : DisposeDataListener {
                                    override fun onSuccess(any: Any) {

                                    }

                                    override fun onFailed(any: Any) {

                                    }

                                }
                        ) {}
                ) {}
        )
    }
}