package com.yan.modularization.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.yan.modularization.R
import com.yan.modularization.module.recommend.RecommendBodyValue
import com.yan.modulesdk.imageloader.ImageLoaderManager

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/10 11:10
 *  @description : 首页listview中第三种item布局
 */
class CardThreeItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mImageLoader: ImageLoaderManager

    init {
        LayoutInflater.from(context).inflate(R.layout.item_product_card_three_layout, this)
        mImageLoader = ImageLoaderManager.getInstance(context)
    }

    fun bindView(bean: RecommendBodyValue) {

    }
}