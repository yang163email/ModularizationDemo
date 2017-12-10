package com.yan.modularization.widget.home

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import com.yan.modularization.R
import com.yan.modularization.adapter.PhotoPagerAdapter
import com.yan.modularization.module.recommend.RecommendFooterValue
import com.yan.modularization.module.recommend.RecommendHeadValue
import com.yan.modulesdk.imageloader.ImageLoaderManager
import kotlinx.android.synthetic.main.listview_home_head_layout.view.*
import org.jetbrains.anko.collections.forEachWithIndex

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/10 15:29
 *  @description : 首页头部view
 */
class HomeHeaderLayout(
        context: Context, attrs: AttributeSet?,
        private val mHeaderValue: RecommendHeadValue
) : RelativeLayout(context, attrs) {

    /**
     * UI
     */
    private var mAdapter: PhotoPagerAdapter

    private val mImageLoader = ImageLoaderManager.getInstance(context)

    constructor(context: Context, headerValue: RecommendHeadValue) : this(context, null, headerValue)

    init {
        LayoutInflater.from(context).inflate(R.layout.listview_home_head_layout, this)
        val imageViews = arrayOf<ImageView>(
                head_image_one, head_image_two, head_image_three, head_image_four)

        mAdapter = PhotoPagerAdapter(context, mHeaderValue.ads!!, true)
        view_pager.apply {
            adapter = mAdapter
            startAutoScroll(3000)
        }
        pager_indictor_view.setViewPager(view_pager)

        imageViews.forEachWithIndex {i, imageView ->
            mImageLoader.displayImage(imageView, mHeaderValue.middle?.get(i))
        }

        mHeaderValue.footer?.forEach {
            content_layout.addView(createItem(it))
        }

        zuixing_view.text = context.getString(R.string.today_zuixing)
    }

    private fun createItem(value: RecommendFooterValue) = HomeBottomItem(context, value)
}