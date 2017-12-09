package com.yan.modularization.base

import java.io.Serializable

/**
 * @description 实体基类
 * @author fangyan
 * @date 2015年8月1日
 */
open class BaseModel : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
