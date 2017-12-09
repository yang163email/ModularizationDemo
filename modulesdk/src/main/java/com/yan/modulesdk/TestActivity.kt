package com.yan.modulesdk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yan.modulesdk.okhttp.request.CommonRequest
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

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
                CommonRequest.createGetRequest("www.hhh"),
                object : Callback{
            override fun onFailure(call: Call?, e: IOException?) {

            }

            override fun onResponse(call: Call?, response: Response?) {
            }

        })
    }
}