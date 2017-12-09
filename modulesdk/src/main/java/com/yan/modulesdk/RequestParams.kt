package com.yan.modulesdk

import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 14:12
 *  @description ：请求参数容器类
 */
class RequestParams(source: Map<String, String>? = null) {

    var urlParams = ConcurrentHashMap<String, String>()
    var fileParams = ConcurrentHashMap<String, Any>()

    init {
        source?.let { outer ->
            outer.forEach {
                putStr(it.key, it.value)
            }
        }
    }

    /**
     * Constructs a new RequestParams instance and populate it with a single
     * initial key/value string param.
     *
     * @param key   the key name for the intial param.
     * @param value the value string for the initial param.
     */
    constructor(key: String, value: String) : this(object : HashMap<String, String>() {
        init { put(key, value) }
    })

    /**
     * Adds a key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for the new param.
     */
    fun putStr(key: String?, value: String?) {
        if (key != null && value != null) {
            urlParams.put(key, value)
        }
    }

    fun putAny(key: String?, any: Any) {
        key?.let { fileParams.put(it, any) }
    }

    fun hasParams() = urlParams.size > 0 || fileParams.size > 0
}