package com.yan.modularization.module.user

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:51
 * @description : User
 */
data class User(
        var ecode: Int?,
        var emsg: String?,
        var data: UserContent?
) : BaseModel()