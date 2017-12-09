package com.yan.modularization.module.course

import com.yan.modularization.base.BaseModel

/**
 * @author      : 楠GG
 * @date        : 2017/12/9 21:37
 * @description : CourseCommentValue
 */
data class CourseCommentValue(
        var text: String?,
        var name: String?,
        var logo: String?,
        var type: Int?,
        var userId: String? //评论所属用户ID
) : BaseModel()