package com.yan.modularization.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import com.github.chrisbanes.photoview.PhotoView
import com.yan.modulesdk.imageloader.ImageLoaderManager
import java.util.*

/**
 * @author      : 楠GG
 * @date        : 2017/12/10 15:18
 * @description : PhotoPagerAdapter
 */
class PhotoPagerAdapter(
        private val mContext: Context,
        private val mData: ArrayList<String>,
        private val mIsMatch: Boolean) : PagerAdapter() {

    private val mLoader: ImageLoaderManager = ImageLoaderManager.getInstance(mContext)

    override fun getCount(): Int = mData.size

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val photoView: ImageView
        if (mIsMatch) {
            photoView = ImageView(mContext)
            photoView.scaleType = ScaleType.FIT_XY
        } else {
            photoView = PhotoView(mContext)
        }
        mLoader.displayImage(photoView, mData[position], null, null)
        container.addView(photoView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT)
        return photoView
    }

    override fun destroyItem(container: ViewGroup, position: Int, any: Any) =
        container.removeView(any as View)

    override fun isViewFromObject(view: View, any: Any): Boolean = view === any
}
