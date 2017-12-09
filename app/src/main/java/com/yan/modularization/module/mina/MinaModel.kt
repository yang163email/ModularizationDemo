package com.yan.modularization.module.mina

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:42
 * @description : MinaModel
 */
data class MinaModel(
        var ecode: Int?,
        var emsg: String?,
        var data: MinaContent?
) : BaseModel()