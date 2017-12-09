package com.yan.modulesdk.okhttp.exception

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 16:01
 *  @description : 自定义异常类,返回ecode,emsg到业务层
 */
class OkHttpException(val ecode: Int, val emsg: Any?) : Exception() {
    private val serialVersionUID = 1L
}