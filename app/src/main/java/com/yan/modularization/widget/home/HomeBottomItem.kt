package com.yan.modularization.widget.home

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.yan.modularization.R
import com.yan.modularization.module.recommend.RecommendFooterValue
import com.yan.modulesdk.imageloader.ImageLoaderManager
import kotlinx.android.synthetic.main.item_home_recommand_layout.view.*

/**
 * @author      : 楠GG
 * @date        : 2017/12/10 15:40
 * @description : HomeBottomItem
 */
class HomeBottomItem(
        context: Context, attrs: AttributeSet?,
        mData: RecommendFooterValue
) : RelativeLayout(context, attrs) {

    private val mImageLoader = ImageLoaderManager.getInstance(context)

    constructor(context: Context, data: RecommendFooterValue) : this(context, null, data)

    init {
        LayoutInflater.from(context).inflate(R.layout.item_home_recommand_layout, this) //添加到当前布局中

        title_view.text = mData.title
        info_view.text = mData.info
        interesting_view.text = mData.from
        mImageLoader.displayImage(icon_1, mData.imageOne)
        mImageLoader.displayImage(icon_2, mData.imageTwo)
    }

}
