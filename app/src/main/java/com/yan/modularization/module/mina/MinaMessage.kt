package com.yan.modularization.module.mina

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:59
 * @description : MinaMessage
 */
data class MinaMessage(
        var type: Int?,
        var title: String?,
        var contentUrl: String?,
        var dayTime: String?,
        var site: String?,
        var photoUrl: String?,
        var imageUrl: String?,
        var info: String?,
        var qq: String?
) : BaseModel()
