package com.yan.modularization.module.mina

import com.yan.modularization.base.BaseModel
import java.util.*

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:41
 * @description : MinaContent
 */
data class MinaContent(
        var systemValues: ArrayList<MinaMessage>?,
        var zanValues: ArrayList<MinaMessage>?,
        var msgValues: ArrayList<MinaMessage>?
) : BaseModel()