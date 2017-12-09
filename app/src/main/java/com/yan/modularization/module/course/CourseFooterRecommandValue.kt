package com.yan.modularization.module.course

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:38
 * @description : CourseFooterRecommandValue
 */
data class CourseFooterRecommandValue(
        var imageUrl: String?,
        var name: String?,
        var price: String?,
        var zan: String?,
        var courseId: String?
) : BaseModel()
