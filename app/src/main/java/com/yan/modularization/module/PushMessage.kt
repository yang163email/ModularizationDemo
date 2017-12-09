package com.yan.modularization.module

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:29
 * @description ：极光推送消息实体，包含所有的数据字段。
 */
data class PushMessage(
        // 消息类型
        var messageType: String?,
        // 连接
        var messageUrl: String?,
        // 详情内容
        var messageContent: String?
) : BaseModel()
