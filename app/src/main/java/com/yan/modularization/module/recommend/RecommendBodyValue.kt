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
        var type: Int = 0,
        var logo: String? = null,
        var title: String? = null,
        var info: String? = null,
        var price: String? = null,
        var text: String? = null,
        var site: String? = null,
        var from: String? = null,
        var zan: String? = null,
        var url: ArrayList<String>? = null,

        //视频专用
        var thumb: String? = null,
        var resource: String? = null,
        var resourceID: String? = null,
        var adid: String? = null,
        var startMonitor: ArrayList<Monitor>? = null,
        var middleMonitor: ArrayList<Monitor>? = null,
        var endMonitor: ArrayList<Monitor>? = null,
        var clickUrl: String? = null,
        var clickMonitor: ArrayList<Monitor>? = null,
        var event: EMEvent? = null
) : BaseModel()