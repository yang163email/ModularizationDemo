package com.yan.modularization.module.update

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:50
 * @description : UpdateModel
 */
data class UpdateModel(
        var ecode: Int?,
        var emsg: String?,
        var data: UpdateInfo?
) : BaseModel()