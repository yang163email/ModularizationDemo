package com.yan.modularization.widget.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.yan.modularization.R
import com.yan.modularization.module.recommend.RecommendBodyValue
import com.yan.modulesdk.imageloader.ImageLoaderManager
import kotlinx.android.synthetic.main.item_product_card_two_layout.view.*

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/10 11:10
 *  @description : 首页listview中第二种item布局
 */
class CardTwoItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mImageLoader: ImageLoaderManager

    init {
        LayoutInflater.from(context).inflate(R.layout.item_product_card_two_layout, this)
        mImageLoader = ImageLoaderManager.getInstance(context)
    }

    @SuppressLint("SetTextI18n")
    fun bindView(bean: RecommendBodyValue) {
        mImageLoader.displayImage(item_logo_view, bean.logo)
        item_title_view.text = bean.title
        item_info_view.text = bean.info
        item_footer_view.text = bean.text + context.getString(R.string.tian_qian)
        item_price_view.text = bean.price
        item_from_view.text = bean.from
        item_zan_view.text = context.getString(R.string.dian_zan) + bean.zan

        mImageLoader.displayImage(product_photo_view, bean.url?.get(0))
    }
}