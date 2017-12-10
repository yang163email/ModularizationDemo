package com.yan.modularization.widget.home

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.yan.modularization.R
import com.yan.modularization.adapter.HotSalePagerAdapter
import com.yan.modularization.module.recommend.RecommendBodyValue
import com.yan.modulesdk.imageloader.ImageLoaderManager
import kotlinx.android.synthetic.main.item_product_card_three_layout.view.*
import org.jetbrains.anko.dip
import java.util.*

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

    fun bindView(list: ArrayList<RecommendBodyValue>) {
        view_pager.apply {
            pageMargin = dip(12)
            adapter = HotSalePagerAdapter(context, list)
            val i = Int.MAX_VALUE / 2
            currentItem = list.size % i
        }
    }
}