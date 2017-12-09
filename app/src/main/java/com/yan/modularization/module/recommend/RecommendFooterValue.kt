package com.yan.modularization.module.recommend

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:26
 * @description ：RecommendFooterValue
 */
data class RecommendFooterValue(
        var title: String?,
        var info: String?,
        var from: String?,
        var imageOne: String?,
        var imageTwo: String?,
        var destationUrl: String?
) : BaseModel()