package com.yan.modularization.module.search

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:47
 * @description : 产品实体
 */
data class ProductModel(
        var _id: String?,
        var fdcode: String?,
        var abbrev: String?,
        var spell: String?,
        var type: String?,
        var time: String? = "10000"
) : BaseModel()