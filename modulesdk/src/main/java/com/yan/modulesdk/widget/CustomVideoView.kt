package com.yan.modulesdk.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.graphics.drawable.AnimationDrawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView.ScaleType
import android.widget.RelativeLayout
import com.yan.modulesdk.R
import com.yan.modulesdk.adutils.LogUtils
import com.yan.modulesdk.adutils.Utils
import com.yan.modulesdk.constant.SDKConstant
import com.yan.modulesdk.core.AdParameters
import kotlinx.android.synthetic.main.xadsdk_video_player.view.*


/**
 *  @author      : 楠GG
 *  @date        : 2017/12/11 19:31
 *  @description : 负责广告播放，暂停，事件触发
 */
class CustomVideoView(context: Context,
                      private val mParentContainer: ViewGroup) : RelativeLayout(context),
        View.OnClickListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener,
        TextureView.SurfaceTextureListener {

    companion object {
        /**
         * Constant
         */
        private val TAG = "MraidVideoView"
        private val TIME_MSG = 0x01
        private val TIME_INVAL = 1000
        private val STATE_ERROR = -1
        private val STATE_IDLE = 0
        private val STATE_PLAYING = 1
        private val STATE_PAUSING = 2
        private val LOAD_TOTAL_COUNT = 3
    }

    /**
     * UI
     */
    private val audioManager: AudioManager?
    private var videoSurface: Surface? = null

    /**
     * Data
     */
    private var mUrl: String? = null
    private var mFrameURI: String? = null
    private var isMute: Boolean = false
    private var mScreenWidth: Int = 0
    private var mDestationHeight: Int = 0

    /**
     * Status状态保护
     */
    private var canPlay = true
    var isRealPause: Boolean = false
    var isComplete: Boolean = false
    private var mCurrentCount: Int = 0
    private var playerState = STATE_IDLE

    private var mediaPlayer: MediaPlayer? = null
    private var listener: ADVideoPlayerListener? = null
    private var mScreenReceiver: ScreenEventReceiver? = null

    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                TIME_MSG -> if (isPlaying) {
                    //还可以在这里更新progressbar
                    //LogUtils.i(TAG, "TIME_MSG");
                    listener?.onBufferUpdate(currentPosition)
                    sendEmptyMessageDelayed(TIME_MSG, TIME_INVAL.toLong())
                }
            }
        }
    }

    private var mFrameLoadListener: ADFrameImageLoadListener? = null

    val isPlaying: Boolean
        get() = mediaPlayer != null && mediaPlayer!!.isPlaying

    val isFrameHidden: Boolean
        get() = framing_view.visibility != View.VISIBLE

    val currentPosition: Int
        get() = mediaPlayer?.currentPosition ?: 0

    val duration: Int
        get() = mediaPlayer?.duration ?: 0

    init {
        audioManager = getContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        initData()
        LayoutInflater.from(this.context).inflate(R.layout.xadsdk_video_player, this)
        initView()
        registerBroadcastReceiver()
    }

    private fun initData() {
        val dm = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)
        mScreenWidth = dm.widthPixels
        mDestationHeight = (mScreenWidth * SDKConstant.VIDEO_HEIGHT_PERCENT).toInt()
    }

    private fun initView() {
        xadsdk_player_video_textureView.apply {
            setOnClickListener(this@CustomVideoView)
            keepScreenOn = true
            surfaceTextureListener = this@CustomVideoView
        }
        initSmallLayoutMode() //init the small mode
    }


    // 小模式状态
    private fun initSmallLayoutMode() {
        val params = RelativeLayout.LayoutParams(mScreenWidth, mDestationHeight)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layoutParams = params

        xadsdk_small_play_btn.setOnClickListener(this)
        xadsdk_to_full_view.setOnClickListener(this)
    }

    fun isShowFullBtn(isShow: Boolean) {
        xadsdk_to_full_view.apply {
            setImageResource(if (isShow) R.drawable.xadsdk_ad_mini else R.drawable.xadsdk_ad_mini_null)
            visibility = if (isShow) View.VISIBLE else View.GONE
        }
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        LogUtils.e(TAG, "onVisibilityChanged" + visibility)
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == View.VISIBLE && playerState == STATE_PAUSING) {
            if (isRealPause || isComplete) {
                pause()
            } else {
                decideCanPlay()
            }
        } else {
            pause()
        }
    }

    override fun onDetachedFromWindow() {
        LogUtils.i(TAG, "onDetachedFromWindow")
        super.onDetachedFromWindow()
    }


    override fun onTouchEvent(ev: MotionEvent): Boolean = true

    /**
     * true is no voice
     *
     * @param mute
     */
    fun mute(mute: Boolean) {
        LogUtils.d(TAG, "mute")
        isMute = mute
        if (mediaPlayer != null && this.audioManager != null) {
            val volume = if (isMute) 0.0f else 1.0f
            mediaPlayer!!.setVolume(volume, volume)
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.xadsdk_small_play_btn -> {
                if (this.playerState == STATE_PAUSING) {
                    if (Utils.getVisiblePercent(mParentContainer) > SDKConstant.VIDEO_SCREEN_PERCENT) {
                        resume()
                        this.listener?.onClickPlay()
                    }
                } else load()
            }
            R.id.xadsdk_to_full_view -> this.listener?.onClickFullScreenBtn()
            R.id.xadsdk_player_video_textureView -> this.listener?.onClickVideo()
        }
    }

    override fun onCompletion(mp: MediaPlayer) {
        if (listener != null) {
            listener!!.onAdVideoLoadComplete()
        }
        playBack()
        isComplete = true
        isRealPause = true
    }

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        LogUtils.e(TAG, "do error:" + what)
        this.playerState = STATE_ERROR
        mediaPlayer = mp
        if (mediaPlayer != null) {
            mediaPlayer!!.reset()
        }
        if (mCurrentCount >= LOAD_TOTAL_COUNT) {
            showPauseView(false)
            if (this.listener != null) {
                listener!!.onAdVideoLoadFailed()
            }
        }
        this.stop()//去重新load
        return true
    }

    override fun onInfo(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        return true
    }

    override fun onPrepared(mp: MediaPlayer) {
        LogUtils.i(TAG, "onPrepared")
        showPlayView()
        mediaPlayer = mp
        if (mediaPlayer != null) {
            mediaPlayer!!.setOnBufferingUpdateListener(this)
            mCurrentCount = 0
            if (listener != null) {
                listener!!.onAdVideoLoadSuccess()
            }
            //满足自动播放条件，则直接播放
            if (Utils.canAutoPlay(context,
                    AdParameters.currentSetting) && Utils.getVisiblePercent(mParentContainer) > SDKConstant.VIDEO_SCREEN_PERCENT) {
                setCurrentPlayState(STATE_PAUSING)
                resume()
            } else {
                setCurrentPlayState(STATE_PLAYING)
                pause()
            }
        }
    }


    override fun onBufferingUpdate(mp: MediaPlayer, percent: Int) {}

    fun setDataSource(url: String) {
        this.mUrl = url
    }

    fun setFrameURI(url: String) {
        mFrameURI = url
    }

    fun load() {
        if (this.playerState != STATE_IDLE) {
            return
        }
        LogUtils.d(TAG, "do play url = " + this.mUrl!!)
        showLoadingView()
        try {
            setCurrentPlayState(STATE_IDLE)
            checkMediaPlayer()
            mute(true)
            mediaPlayer!!.setDataSource(this.mUrl)
            mediaPlayer!!.prepareAsync() //开始异步加载
        } catch (e: Exception) {
            LogUtils.e(TAG, e.message)
            stop() //error以后重新调用stop加载
        }

    }

    fun pause() {
        if (this.playerState != STATE_PLAYING) {
            return
        }
        LogUtils.d(TAG, "do pause")
        setCurrentPlayState(STATE_PAUSING)
        if (isPlaying) {
            mediaPlayer!!.pause()
            if (!this.canPlay) {
                this.mediaPlayer!!.seekTo(0)
            }
        }
        this.showPauseView(false)
        mHandler.removeCallbacksAndMessages(null)
    }

    //全屏不显示暂停状态,后续可以整合，不必单独出一个方法
    fun pauseForFullScreen() {
        if (playerState != STATE_PLAYING) {
            return
        }
        LogUtils.d(TAG, "do full pause")
        setCurrentPlayState(STATE_PAUSING)
        if (isPlaying) {
            mediaPlayer!!.pause()
            if (!this.canPlay) {
                mediaPlayer!!.seekTo(0)
            }
        }
        mHandler.removeCallbacksAndMessages(null)
    }

    //跳到指定点播放视频
    fun seekAndResume(position: Int) {
        if (mediaPlayer != null) {
            showPauseView(true)
            entryResumeState()
            mediaPlayer!!.seekTo(position)
            mediaPlayer!!.setOnSeekCompleteListener {
                LogUtils.d(TAG, "do seek and resume")
                mediaPlayer!!.start()
                mHandler.sendEmptyMessage(TIME_MSG)
            }
        }
    }

    //跳到指定点暂停视频
    fun seekAndPause(position: Int) {
        if (this.playerState != STATE_PLAYING) {
            return
        }
        showPauseView(false)
        setCurrentPlayState(STATE_PAUSING)
        if (isPlaying) {
            mediaPlayer!!.seekTo(position)
            mediaPlayer!!.setOnSeekCompleteListener {
                LogUtils.d(TAG, "do seek and pause")
                mediaPlayer!!.pause()
                mHandler.removeCallbacksAndMessages(null)
            }
        }
    }

    fun resume() {
        if (this.playerState != STATE_PAUSING) {
            return
        }
        LogUtils.d(TAG, "do resume")
        if (!isPlaying) {
            entryResumeState()
            mediaPlayer!!.setOnSeekCompleteListener(null)
            mediaPlayer!!.start()
            mHandler.sendEmptyMessage(TIME_MSG)
            showPauseView(true)
        } else {
            showPauseView(false)
        }
    }

    /**
     * 进入播放状态时的状态更新
     */
    private fun entryResumeState() {
        canPlay = true
        setCurrentPlayState(STATE_PLAYING)
        isRealPause = false
        isComplete = false
    }

    private fun setCurrentPlayState(state: Int) {
        playerState = state
    }

    //播放完成后回到初始状态
    fun playBack() {
        LogUtils.d(TAG, " do playBack")
        setCurrentPlayState(STATE_PAUSING)
        mHandler.removeCallbacksAndMessages(null)
        if (mediaPlayer != null) {
            mediaPlayer!!.setOnSeekCompleteListener(null)
            mediaPlayer!!.seekTo(0)
            mediaPlayer!!.pause()
        }
        this.showPauseView(false)
    }

    fun stop() {
        LogUtils.d(TAG, " do stop")
        if (this.mediaPlayer != null) {
            this.mediaPlayer!!.reset()
            this.mediaPlayer!!.setOnSeekCompleteListener(null)
            this.mediaPlayer!!.stop()
            this.mediaPlayer!!.release()
            this.mediaPlayer = null
        }
        mHandler.removeCallbacksAndMessages(null)
        setCurrentPlayState(STATE_IDLE)
        if (mCurrentCount < LOAD_TOTAL_COUNT) { //满足重新加载的条件
            mCurrentCount += 1
            load()
        } else {
            showPauseView(false) //显示暂停状态
        }
    }

    fun destroy() {
        LogUtils.d(TAG, " do destroy")
        if (this.mediaPlayer != null) {
            this.mediaPlayer!!.setOnSeekCompleteListener(null)
            this.mediaPlayer!!.stop()
            this.mediaPlayer!!.release()
            this.mediaPlayer = null
        }
        setCurrentPlayState(STATE_IDLE)
        mCurrentCount = 0
        isComplete = false
        isRealPause = false
        unRegisterBroadcastReceiver()
        mHandler.removeCallbacksAndMessages(null) //release all message and runnable
        showPauseView(false) //除了播放和loading外其余任何状态都显示pause
    }

    fun setListener(listener: ADVideoPlayerListener) {
        this.listener = listener
    }

    fun setFrameLoadListener(frameLoadListener: ADFrameImageLoadListener) {
        this.mFrameLoadListener = frameLoadListener
    }

    @Synchronized private fun checkMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = createMediaPlayer() //每次都重新创建一个新的播放器
        }
    }

    private fun createMediaPlayer(): MediaPlayer? {
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.reset()
        mediaPlayer!!.setOnPreparedListener(this)
        mediaPlayer!!.setOnCompletionListener(this)
        mediaPlayer!!.setOnInfoListener(this)
        mediaPlayer!!.setOnErrorListener(this)
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        if (videoSurface != null && videoSurface!!.isValid) {
            mediaPlayer!!.setSurface(videoSurface)
        } else {
            stop()
        }
        return mediaPlayer
    }

    private fun showPauseView(show: Boolean) {
        xadsdk_to_full_view.visibility = if (show) View.VISIBLE else View.GONE
        xadsdk_small_play_btn.visibility = if (show) View.GONE else View.VISIBLE
        loading_bar.clearAnimation()
        loading_bar.visibility = View.GONE
        if (!show) {
            framing_view.visibility = View.VISIBLE
            loadFrameImage()
        } else {
            framing_view.visibility = View.GONE
        }
    }

    private fun showLoadingView() {
        xadsdk_to_full_view.visibility = View.GONE
        loading_bar.visibility = View.VISIBLE
        val anim = loading_bar.background as AnimationDrawable
        anim.start()
        xadsdk_small_play_btn.visibility = View.GONE
        framing_view.visibility = View.GONE
        loadFrameImage()
    }

    private fun showPlayView() {
        loading_bar.clearAnimation()
        loading_bar.visibility = View.GONE
        xadsdk_small_play_btn.visibility = View.GONE
        framing_view.visibility = View.GONE
    }

    /**
     * 异步加载定帧图
     */
    private fun loadFrameImage() {
        if (mFrameLoadListener != null) {
            mFrameLoadListener!!.onStartFrameLoad(mFrameURI, object : ImageLoaderListener {

                override fun onLoadingComplete(loadedImage: Bitmap?) {
                    if (loadedImage != null) {
                        framing_view.scaleType = ScaleType.FIT_XY
                        framing_view.setImageBitmap(loadedImage)
                    } else {
                        framing_view.scaleType = ScaleType.FIT_CENTER
                        framing_view.setImageResource(R.drawable.xadsdk_img_error)
                    }
                }
            })
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        LogUtils.i(TAG, "onSurfaceTextureAvailable")
        videoSurface = Surface(surface)
        checkMediaPlayer()
        mediaPlayer!!.setSurface(videoSurface)
        load()
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
        LogUtils.i(TAG, "onSurfaceTextureSizeChanged")
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        return false
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}

    private fun registerBroadcastReceiver() {
        if (mScreenReceiver == null) {
            mScreenReceiver = ScreenEventReceiver()
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_SCREEN_OFF)
            filter.addAction(Intent.ACTION_USER_PRESENT)
            context.registerReceiver(mScreenReceiver, filter)
        }
    }

    private fun unRegisterBroadcastReceiver() {
        if (mScreenReceiver != null) {
            context.unregisterReceiver(mScreenReceiver)
        }
    }

    private fun decideCanPlay() {
        if (Utils.getVisiblePercent(mParentContainer) > SDKConstant.VIDEO_SCREEN_PERCENT)
        //来回切换页面时，只有 >50,且满足自动播放条件才自动播放
            resume()
        else
            pause()
    }

    /**
     * 监听锁屏事件的广播接收器
     */
    private inner class ScreenEventReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //主动锁屏时 pause, 主动解锁屏幕时，resume
            when (intent.action) {
                Intent.ACTION_USER_PRESENT -> if (playerState == STATE_PAUSING) {
                    if (isRealPause) {
                        //手动点的暂停，回来后还暂停
                        pause()
                    } else {
                        decideCanPlay()
                    }
                }
                Intent.ACTION_SCREEN_OFF -> if (playerState == STATE_PLAYING) {
                    pause()
                }
            }
        }
    }

    /**
     * 供slot层来实现具体点击逻辑,具体逻辑还会变，
     * 如果对UI的点击没有具体监测的话可以不回调
     */
    interface ADVideoPlayerListener {

        fun onBufferUpdate(time: Int)

        fun onClickFullScreenBtn()

        fun onClickVideo()

        fun onClickBackBtn()

        fun onClickPlay()

        fun onAdVideoLoadSuccess()

        fun onAdVideoLoadFailed()

        fun onAdVideoLoadComplete()
    }

    interface ADFrameImageLoadListener {

        fun onStartFrameLoad(url: String?, listener: ImageLoaderListener)
    }

    interface ImageLoaderListener {
        /**
         * 如果图片下载不成功，传null
         *
         * @param loadedImage
         */
        fun onLoadingComplete(loadedImage: Bitmap?)
    }
}