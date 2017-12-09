package com.yan.modularization.module.recommend

import com.yan.modularization.base.BaseModel
import java.util.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 21:26
 *  @description ：RecommendHeadValue
 */
data class RecommendHeadValue(
        var ads: ArrayList<String>?,
        var middle: ArrayList<String>?,
        var footer: ArrayList<RecommendFooterValue>?
) : BaseModel()
