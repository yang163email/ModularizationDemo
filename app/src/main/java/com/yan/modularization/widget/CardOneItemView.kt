package com.yan.modularization.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.yan.modularization.R
import com.yan.modularization.module.recommend.RecommendBodyValue
import com.yan.modulesdk.imageloader.ImageLoaderManager
import kotlinx.android.synthetic.main.item_product_card_one_layout.view.*
import org.jetbrains.anko.dip

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/10 11:10
 *  @description : 首页listview中第一种item布局
 */
class CardOneItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mImageLoader: ImageLoaderManager

    init {
        LayoutInflater.from(context).inflate(R.layout.item_product_card_one_layout, this)
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

        //添加前，移除所有的view，防止复用时出现问题
        product_photo_layout.removeAllViews()
        //动态添加多个view
        bean.url?.forEach {
            product_photo_layout.addView(createImageView(it))
        }
    }

    private fun createImageView(url: String): ImageView {
        val imageView = ImageView(context)
        //设置布局参数
        val params = LinearLayout.LayoutParams(
                dip(100), LinearLayout.LayoutParams.MATCH_PARENT)
        params.leftMargin = dip(5)
        imageView.layoutParams = params
        mImageLoader.displayImage(imageView, url)
        return imageView
    }

}