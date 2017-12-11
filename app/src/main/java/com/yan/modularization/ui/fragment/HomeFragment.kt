package com.yan.modularization.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yan.modularization.R
import com.yan.modularization.adapter.CourseAdapter
import com.yan.modularization.base.BaseFragment
import com.yan.modularization.module.recommend.BaseRecommendModel
import com.yan.modularization.net.http.RequestCenter
import com.yan.modularization.widget.home.HomeHeaderLayout
import com.yan.modularization.zxing.app.CaptureActivity
import com.yan.modulesdk.activity.AdBrowserActivity
import com.yan.modulesdk.okhttp.listener.DisposeDataListener
import kotlinx.android.synthetic.main.fragment_home_layout.*
import org.jetbrains.anko.toast

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 11:45
 *  @description : 首页fragment
 */
class HomeFragment : BaseFragment() {

    companion object {
        val REQ_CODE = 10
        val REQ_QR_CODE = 0x01
    }

    var mRecommendData: BaseRecommendModel? = null
    var mCourseAdapter: CourseAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_home_layout, container, false)
    }

    override fun initView() {
        super.initView()
        val drawable = loading_view.drawable as AnimationDrawable
        drawable.start()
    }

    override fun initListener() {
        qrcode_view.setOnClickListener { checkPermission() }
    }

    /**
     * 检查权限
     */
    private fun checkPermission() {
        val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), REQ_CODE)
        } else doOpenCamera()
    }

    private fun doOpenCamera() {
        val intent = Intent(context, CaptureActivity::class.java)
        startActivityForResult(intent, REQ_QR_CODE)
    }

    override fun initData() {
        super.initData()
        RequestCenter.requestRecommendData(object : DisposeDataListener {
            override fun onSuccess(responseObj: Any) {
                Log.e(TAG, "onSuccess: $responseObj")
                //获取到数据后更新UI
                mRecommendData = responseObj as BaseRecommendModel
                showSuccessView()
            }

            override fun onFailed(responseObj: Any) {
                Log.e(TAG, "onFailed: $responseObj")
            }
        })
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doOpenCamera()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_QR_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val code = data?.getStringExtra("SCAN_RESULT")?:""
                    Log.e(TAG, "onActivityResult: $code")
                    if (code.contains("http") || code.contains("https")) {
                        val intent = Intent(mContext, AdBrowserActivity::class.java)
                        intent.putExtra(AdBrowserActivity.KEY_URL, code)
                        startActivity(intent)
                    } else {
                        Toast.makeText(mContext, code, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showSuccessView() {
        val list = mRecommendData?.data?.list
        if (list != null && list.size > 0) {
            loading_view.visibility = View.GONE
            list_view.visibility = View.VISIBLE

            //给listview添加头布局
            var homeHeaderLayout = HomeHeaderLayout(context, mRecommendData!!.data!!.head!!)
            list_view.addHeaderView(homeHeaderLayout)
            //创建adapter
            mCourseAdapter = CourseAdapter(mContext, list)
            list_view.adapter = mCourseAdapter
        } else {
            //没有数据、数据异常
            showErrorView()
        }
    }

    private fun showErrorView() {
        mContext.toast("异常啦")
    }
}