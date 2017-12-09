package com.yan.modulesdk.okhttp.response

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.yan.modulesdk.okhttp.exception.OkHttpException
import com.yan.modulesdk.okhttp.listener.DisposeDataHandle
import com.yan.modulesdk.okhttp.listener.DisposeDataListener
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 15:54
 *  @description : 专门处理JSON的回调
 */
open class CommonJsonCallback(handle: DisposeDataHandle) : Callback{

    /**
     * the logic layer exception, may alter in different app
     */
    protected val RESULT_CODE = "ecode" // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected val RESULT_CODE_VALUE = 0
    protected val ERROR_MSG = "emsg"
    protected val EMPTY_MSG = ""
    protected val COOKIE_STORE = "Set-Cookie" // decide the server it
    // can has the value of
    // set-cookie2

    /**
     * the java layer exception, do not same to the logic error
     */
    protected val NETWORK_ERROR = -1 // the network relative error
    protected val JSON_ERROR = -2 // the JSON relative error
    protected val OTHER_ERROR = -3 // the unknow error

    /**
     * 将其它线程的数据转发到UI线程
     */
    private val mDeliveryHandler: Handler
    private val mListener: DisposeDataListener?
    private val mClass: Class<*>?

    init {
        mListener = handle.mListener
        mClass = handle.mClass
        mDeliveryHandler = Handler(Looper.getMainLooper())
    }

    override fun onFailure(call: Call?, e: IOException?) {
        mDeliveryHandler.post {
            mListener?.onFailed(OkHttpException(NETWORK_ERROR, e))
        }
    }

    override fun onResponse(call: Call?, response: Response?) {
        response?.let { 
            val result = it.body()?.string()
            mDeliveryHandler.post {
                handleResponse(result)
            }
        }
    }

    /**
     * 处理服务器的响应
     */
    private fun handleResponse(result: Any?) {
        if (result == null || result.toString().trim() == "") {
            mListener?.onFailed(OkHttpException(NETWORK_ERROR, EMPTY_MSG))
            return
        }

        try {
            val jsonObj = JSONObject(result.toString())
            //开始解析json
            if (jsonObj.has(RESULT_CODE)) {
                //从json对象中取出响应码，若为0，则是正确的响应
                if (jsonObj.getInt(RESULT_CODE) == RESULT_CODE_VALUE) {
                    if (mClass == null) {
                        //如果没有传递Class过来，直接返回原数据
                        mListener?.onSuccess(jsonObj)
                    } else {
                        val obj = Gson().fromJson(jsonObj.toString(), mClass)
                        if (obj != null) {
                            mListener?.onSuccess(obj)
                        } else {
                            //不是合法的json
                            mListener?.onFailed(OkHttpException(JSON_ERROR, EMPTY_MSG))
                        }
                    }
                } else {
                    //将服务器返回的异常回调到应用层去处理
                    mListener?.onFailed(OkHttpException(OTHER_ERROR, jsonObj.get(RESULT_CODE)))
                }
            }
        } catch (e: Exception) {
            mListener?.onFailed(OkHttpException(OTHER_ERROR, e.message))
        }
    }
}