package com.yan.modularization.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.yan.modularization.R
import com.yan.modularization.module.recommend.RecommendBodyValue
import com.yan.modulesdk.imageloader.ImageLoaderManager
import kotlinx.android.synthetic.main.item_hot_product_pager_layout.view.*
import org.jetbrains.anko.collections.forEachWithIndex
import java.util.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/10 13:21
 *  @description : 首页listview中viewpager的适配器
 */
class HotSalePagerAdapter(
        private val mContext: Context,
        private val mData: ArrayList<RecommendBodyValue>
) : PagerAdapter() {
    private val mInflate: LayoutInflater
    private val mImageLoader: ImageLoaderManager

    init {
        mInflate = LayoutInflater.from(mContext)
        mImageLoader = ImageLoaderManager.getInstance(mContext)
    }

    override fun getCount(): Int = Integer.MAX_VALUE

    override fun isViewFromObject(view: View?, any: Any?): Boolean = view == any

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) =
        container.removeView(any as View)

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val bean = mData[position % mData.size]
        val rootView = mInflate.inflate(R.layout.item_hot_product_pager_layout,
                container, false)
        val imageViews = arrayOf<ImageView>(
                rootView.image_one, rootView.image_two, rootView.image_three)

        rootView.title_view.text = bean.title
        rootView.info_view.text = bean.price
        rootView.gonggao_view.text = bean.info
        rootView.sale_num_view.text = bean.text
        imageViews.forEachWithIndex{i, imageView ->
            mImageLoader.displayImage(imageView, bean.url?.get(i))
        }
        container.addView(rootView, 0)
        return rootView
    }
}
