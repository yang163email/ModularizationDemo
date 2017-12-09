package com.yan.modularization.module.recommend

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:18
 * @description ：BaseRecommendModel
 */
data class BaseRecommendModel(
        var ecode: String?,
        var emsg: String?,
        var data: RecommendModel?
) : BaseModel()