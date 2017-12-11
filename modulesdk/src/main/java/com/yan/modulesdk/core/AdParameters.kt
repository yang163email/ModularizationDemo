package com.yan.modulesdk.core


import com.yan.modulesdk.constant.SDKConstant.AutoPlaySetting

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/11 19:31
 *  @description : 广告SDK全局参数配置, 都用静态来保证统一性
 */
object AdParameters {

    //用来记录可自动播放的条件
    var currentSetting = AutoPlaySetting.AUTO_PLAY_3G_4G_WIFI //默认都可以自动播放

    /**
     * 获取sdk当前版本号
     */
    fun getAdSDKVersion() = "1.0.0"
}
