package com.yan.modularization.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yan.modularization.module.recommend.RecommendBodyValue
import com.yan.modularization.utils.Util
import com.yan.modularization.widget.home.CardOneItemView
import com.yan.modularization.widget.home.CardThreeItemView
import com.yan.modularization.widget.home.CardTwoItemView
import com.yan.modularization.widget.home.VideoItemView

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 22:53
 *  @description : 首页listview列表适配器
 */
class CourseAdapter(val mContext: Context, val mData: List<RecommendBodyValue>) : BaseAdapter() {

    companion object {
        /** 共4中样式 */
        private val CARD_COUNT = 4
        /** 对应每个样式 */
        private val VIDEO_TYPE = 0x00
        private val CARD_TYPE_ONE = 0x01
        private val CARD_TYPE_TWO = 0x02
        private val CARD_TYPE_THREE = 0x03
    }

    override fun getItem(position: Int): Any = mData[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = mData.size

    override fun getItemViewType(position: Int): Int {
        val value = getItem(position) as RecommendBodyValue
        return value.type
    }

    override fun getViewTypeCount(): Int = CARD_COUNT

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val type = getItemViewType(position)
        var itemView = convertView
        var viewHolder: ViewHolder? = null
        if (convertView == null) {
            when(type) {
                VIDEO_TYPE -> itemView = VideoItemView(mContext)
                CARD_TYPE_ONE -> itemView = CardOneItemView(mContext)
                CARD_TYPE_TWO -> itemView = CardTwoItemView(mContext)
                CARD_TYPE_THREE -> itemView = CardThreeItemView(mContext)
            }
            viewHolder = ViewHolder(itemView)
            itemView?.tag = viewHolder
        } else {
            viewHolder = itemView?.tag as ViewHolder?
        }
        //设置数据
        when(type) {
            VIDEO_TYPE -> {
                val videoItemView = viewHolder?.itemView as VideoItemView
                videoItemView.bindView(mData[position])
            }
            CARD_TYPE_ONE -> {
                val videoItemView = viewHolder?.itemView as CardOneItemView
                videoItemView.bindView(mData[position])
            }
            CARD_TYPE_TWO -> {
                val videoItemView = viewHolder?.itemView as CardTwoItemView
                videoItemView.bindView(mData[position])
            }
            CARD_TYPE_THREE -> {
                val videoItemView = viewHolder?.itemView as CardThreeItemView
                val list = Util.handleData(mData[position])
                videoItemView.bindView(list)
            }
        }
        return itemView
    }

    class ViewHolder(val itemView: View?)
}
