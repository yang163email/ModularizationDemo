package com.yan.modulesdk.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import com.nostra13.universalimageloader.core.download.BaseImageDownloader
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.yan.modulesdk.R

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 17:50
 *  @description : 初始化UniversalImageLoader，并用来加载网络图片
 */
class ImageLoaderManager private constructor(context: Context?){
    /**
     * 存放一些默认参数值
     */
    companion object {
        /** 表示UIL最多可以有多少线程 */
        const val THREAD_COUNT = 4
        /** 标明图片加载的优先级 */
        const val PROPRITY = 2
        /** 标明UIL的磁盘最大缓存大小 */
        const val DISK_CACHE_SIZE = 50 * 1024
        /** 连接超时时间 */
        const val CONNECTION_TIME_OUT = 5 * 100
        /** 读取的超时时间 */
        const val READ_TIME_OUT = 5 * 100

        private var sInstance: ImageLoaderManager? = null

        fun getInstance(context: Context?): ImageLoaderManager {
            if (sInstance == null) {
                synchronized(ImageLoaderManager::class.java) {
                    if (sInstance == null) {
                        sInstance = ImageLoaderManager(context)
                    }
                }
            }
            return sInstance!!
        }
    }

    private var mImageLoader: ImageLoader

    init {
        val configuration = ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(THREAD_COUNT)   //配置图片下载最大线程数
                .threadPriority(Thread.NORM_PRIORITY - PROPRITY)
                .denyCacheImageMultipleSizesInMemory()  //防止多套尺寸的图片到内存中
                .memoryCache(WeakMemoryCache()) //使用弱引用内存缓存
                .diskCacheSize(DISK_CACHE_SIZE) //磁盘缓存大小
                .diskCacheFileNameGenerator(Md5FileNameGenerator()) //使用MD5命名文件
                .tasksProcessingOrder(QueueProcessingType.LIFO) //图片下载顺序
                .defaultDisplayImageOptions(getDefaultOptions())    //默认的图片加载Options
                .imageDownloader(BaseImageDownloader(context, CONNECTION_TIME_OUT,
                        READ_TIME_OUT)) //设置图片下载器
                .writeDebugLogs()   //debug环境下输出日志
                .build()

        ImageLoader.getInstance().init(configuration)
        mImageLoader = ImageLoader.getInstance()
    }

    private fun getDefaultOptions(): DisplayImageOptions? {
        return DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.xadsdk_img_error)  //下载图片为空时
                .showImageOnFail(R.drawable.xadsdk_img_error)   //下载图片出错时
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)    //图片解码类型
                .decodingOptions(BitmapFactory.Options())   //图片解码配置
                .build()
    }

    /**
     * 加载图片API
     */
    fun displayImage(imageView: ImageView,
                     url: String?,
                     options: DisplayImageOptions? = null,
                     listener: ImageLoadingListener? = null) {
        mImageLoader.displayImage(url, imageView, options, listener)
    }

}