package com.yan.modularization.module.course

import com.yan.modularization.base.BaseModel
import java.util.*

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:39
 * @description : CourseFooterValue
 */
data class CourseFooterValue(
        var time: ArrayList<CourseFooterDateValue>?,
        var recommand: ArrayList<CourseFooterRecommandValue>?
) : BaseModel()