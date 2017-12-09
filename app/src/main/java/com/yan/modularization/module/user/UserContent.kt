package com.yan.modularization.module.user

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 22:00
 * @description : UserContent
 */
data class UserContent(
        var userId: String?, //用户唯一标识符
        var photoUrl: String?,
        var name: String?,
        var tick: String?,
        var mobile: String?,
        var platform: String?
) : BaseModel()