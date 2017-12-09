package com.yan.modularization.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 11:30
 *  @description : 所有Activity的基类
 */
abstract class BaseActivity : AppCompatActivity() {

    protected val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
        initListener()
    }

    protected open fun initListener() {

    }

    protected open fun initView() {

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    abstract fun getLayoutResId(): Int
}