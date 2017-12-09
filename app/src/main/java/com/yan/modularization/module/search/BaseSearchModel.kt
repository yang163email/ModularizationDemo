package com.yan.modularization.module.search

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:46
 * @description : BaseSearchModel
 */
data class BaseSearchModel(
        var ecode: String?,
        var emsg: String?,
        var data: SearchModel?
) : BaseModel()
