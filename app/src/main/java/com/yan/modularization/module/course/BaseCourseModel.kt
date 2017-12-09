package com.yan.modularization.module.course

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:37
 * @description : BaseCourseModel
 */
data class BaseCourseModel(
        var ecode: String?,
        var emsg: String?,
        var data: CourseModel?
) : BaseModel()