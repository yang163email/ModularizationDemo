package com.yan.modularization.module.course

import com.yan.modularization.base.BaseModel
import com.yan.modulesdk.module.AdValue
import java.util.*

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:58
 * @description : CourseHeaderValue
 */
data class CourseHeaderValue(
        var photoUrls: ArrayList<String>?,
        var text: String?,
        var name: String?,
        var logo: String?,
        var oldPrice: String?,
        var newPrice: String?,
        var zan: String?,
        var scan: String?,
        var hotComment: String?,
        var from: String?,
        var dayTime: String?,
        var video: AdValue?
) : BaseModel()