package com.yan.modularization.module.search

import com.yan.modularization.base.BaseModel
import java.util.*

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:49
 * @description : SearchModel
 */
data class SearchModel(
        var uptime: Long?,
        var list: ArrayList<ProductModel>?
) : BaseModel()