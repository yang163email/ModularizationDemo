package com.yan.modulesdk.module

import com.yan.modulesdk.module.monitor.Monitor
import com.yan.modulesdk.module.monitor.emevent.EMEvent
import java.util.*

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:31
 * @description : 广告json value节点， 节点名字记得修改一下
 */
data class AdValue(
        var resourceID: String?,
        var adid: String?,
        var resource: String?,
        var thumb: String?,
        var startMonitor: ArrayList<Monitor>?,
        var middleMonitor: ArrayList<Monitor>?,
        var endMonitor: ArrayList<Monitor>?,
        var clickUrl: String?,
        var clickMonitor: ArrayList<Monitor>?,
        var event: EMEvent?,
        var type: String?
)



