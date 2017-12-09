package com.yan.modularization.module.recommend

import com.yan.modularization.base.BaseModel
import java.util.*

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:19
 * @description ：产品实体
 */
data class RecommendModel(
        var list: ArrayList<RecommendBodyValue>?,
        var head: RecommendHeadValue?
) : BaseModel()