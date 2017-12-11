package com.yan.modulesdk.constant

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/11 19:32
 *  @description : 用来存放sdk中用到的常量。
 */
object SDKConstant {

    //毫秒单位
    const val MILLION_UNIT = 1000

    //自动播放阈值
    const val VIDEO_SCREEN_PERCENT = 50

    const val VIDEO_HEIGHT_PERCENT = 9 / 16.0f

    //素材类型
    val MATERIAL_IMAGE = "image"
    val MATERIAL_HTML = "html"

    //自动播放条件
    enum class AutoPlaySetting {
        AUTO_PLAY_ONLY_WIFI,
        AUTO_PLAY_3G_4G_WIFI,
        AUTO_PLAY_NEVER
    }
}
