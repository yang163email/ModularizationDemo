package com.yan.modularization.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yan.modularization.R
import com.yan.modularization.base.BaseFragment
import com.yan.modularization.net.http.RequestCenter
import com.yan.modulesdk.okhttp.listener.DisposeDataListener

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 11:45
 *  @description : 首页fragment
 */
class HomeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_home_layout, container, false)
    }

    override fun initData() {
        super.initData()
        RequestCenter.requestRecommendData(object : DisposeDataListener {
            override fun onSuccess(any: Any) {
                Log.e(TAG, "onSuccess: $any")
            }

            override fun onFailed(any: Any) {
                Log.e(TAG, "onFailed: $any")
            }
        })
    }
}