package com.yan.modularization.module.recommend

import com.yan.modularization.base.BaseModel
import com.yan.modulesdk.module.monitor.Monitor
import com.yan.modulesdk.module.monitor.emevent.EMEvent
import java.util.*

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:43
 * @description : 搜索实体
 */
data class RecommendBodyValue(
        var type: Int?,
        var logo: String?,
        var title: String?,
        var info: String?,
        var price: String?,
        var text: String?,
        var site: String?,
        var from: String?,
        var zan: String?,
        var url: ArrayList<String>?,

        //视频专用
        var thumb: String?,
        var resource: String?,
        var resourceID: String?,
        var adid: String?,
        var startMonitor: ArrayList<Monitor>?,
        var middleMonitor: ArrayList<Monitor>?,
        var endMonitor: ArrayList<Monitor>?,
        var clickUrl: String?,
        var clickMonitor: ArrayList<Monitor>?,
        var event: EMEvent?
) : BaseModel()