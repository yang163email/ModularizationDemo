package com.yan.modularization.module.course

import com.yan.modularization.base.BaseModel
import java.util.*

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:40
 * @description : CourseModel
 */
data class CourseModel(
        var head: CourseHeaderValue?,
        var footer: CourseFooterValue?,
        var body: ArrayList<CourseCommentValue>?
) : BaseModel()