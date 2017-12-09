package com.yan.modularization.base

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 11:33
 *  @description : 所有Fragment的基类
 */
abstract class BaseFragment : Fragment() {
    protected val TAG = javaClass.simpleName

    protected lateinit var mContext: Activity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        init()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    protected open fun initData() {

    }

    protected open fun init() { }

}