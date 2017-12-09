package com.yan.modularization.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.yan.modularization.R
import com.yan.modularization.module.recommend.RecommendBodyValue
import com.yan.modulesdk.imageloader.ImageLoaderManager
import kotlinx.android.synthetic.main.item_video_layout.view.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 23:05
 *  @description : Video条目view
 */
class VideoItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mImageLoader: ImageLoaderManager
    init {
        LayoutInflater.from(context).inflate(R.layout.item_video_layout, this)
        mImageLoader = ImageLoaderManager.getInstance(context)
    }

    @SuppressLint("SetTextI18n")
    fun bindView(bean: RecommendBodyValue) {
        mImageLoader.displayImage(item_logo_view, bean.logo)
        item_title_view.text = bean.title
        item_info_view.text = bean.info + "天前"
        item_footer_view.text = bean.text
    }
}