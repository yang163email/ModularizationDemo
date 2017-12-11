package com.yan.modularization.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.yan.modularization.R
import com.yan.modularization.base.BaseActivity
import com.yan.modularization.ui.fragment.HomeFragment
import com.yan.modularization.ui.fragment.MessageFragment
import com.yan.modularization.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_home_layout.*
import org.jetbrains.anko.dip

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 11:23
 *  @description ：首页Activity
 */
class HomeActivity : BaseActivity(), View.OnClickListener {

    lateinit var fm: FragmentManager
    var mHomeFragment: HomeFragment? = null
    var mMessageFragment: MessageFragment? = null
    var mMineFragment: MineFragment? = null

    override fun getLayoutResId(): Int = R.layout.activity_home_layout

    override fun initView() {
        super.initView()

        //默认选中首页
        home_image_view.setBackgroundResource(R.mipmap.comui_tab_home_selected)
        //第一次创建HomeFragment
        mHomeFragment = HomeFragment()
        fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.content_layout, mHomeFragment)
        fragmentTransaction.commit()
    }

    override fun initListener() {
        super.initListener()
        home_layout_view.setOnClickListener(this)
        message_layout_view.setOnClickListener(this)
        mine_layout_view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val fragmentTransaction = fm.beginTransaction()
        when (v?.id) {
            R.id.home_layout_view -> clickHomeTab(fragmentTransaction)
            R.id.message_layout_view -> clickMessageTab(fragmentTransaction)
            R.id.mine_layout_view -> clickMineTab(fragmentTransaction)
        }
        fragmentTransaction.commit()
    }

    private fun clickMineTab(fragmentTransaction: FragmentTransaction) {
        home_image_view.setBackgroundResource(R.mipmap.comui_tab_home)
        message_image_view.setBackgroundResource(R.mipmap.comui_tab_message)
        mine_image_view.setBackgroundResource(R.mipmap.comui_tab_person_selected)

        hideFragment(mHomeFragment, fragmentTransaction)
        hideFragment(mMessageFragment, fragmentTransaction)
        if (mMineFragment == null) {
            mMineFragment = MineFragment()
            fragmentTransaction.add(R.id.content_layout, mMineFragment)
        }
        fragmentTransaction.show(mMineFragment)
    }

    private fun clickMessageTab(fragmentTransaction: FragmentTransaction) {
        home_image_view.setBackgroundResource(R.mipmap.comui_tab_home)
        message_image_view.setBackgroundResource(R.mipmap.comui_tab_message_selected)
        mine_image_view.setBackgroundResource(R.mipmap.comui_tab_person)

        hideFragment(mHomeFragment, fragmentTransaction)
        hideFragment(mMineFragment, fragmentTransaction)
        if (mMessageFragment == null) {
            mMessageFragment = MessageFragment()
            fragmentTransaction.add(R.id.content_layout, mMessageFragment)
        }
        fragmentTransaction.show(mMessageFragment)
    }

    private fun clickHomeTab(fragmentTransaction: FragmentTransaction) {
        home_image_view.setBackgroundResource(R.mipmap.comui_tab_home_selected)
        message_image_view.setBackgroundResource(R.mipmap.comui_tab_message)
        mine_image_view.setBackgroundResource(R.mipmap.comui_tab_person)

        hideFragment(mMessageFragment, fragmentTransaction)
        hideFragment(mMineFragment, fragmentTransaction)
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment()
            fragmentTransaction.add(R.id.content_layout, mHomeFragment)
        }
        fragmentTransaction.show(mHomeFragment)
    }

    private fun hideFragment(fragment: Fragment?, ft: FragmentTransaction) {
        fragment?.let { ft.hide(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e(TAG, "onActivityResult: requestCode:$requestCode---resultCode:$resultCode---")
        val result = data?.getStringExtra("result")
        val bitmap = data?.getParcelableExtra<Bitmap>("QR_CODE")
        Log.e(TAG, "onActivityResult: $result")
        Log.e(TAG, "onActivityResult: $bitmap")
        //bitmap 是生成的二维码图片
        bitmap?.let {
            val imageView = ImageView(this)
            imageView.setImageBitmap(bitmap)
            val params = RelativeLayout.LayoutParams(dip(200), dip(200))
            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            imageView.layoutParams = params
            content_layout.addView(imageView)
        }
    }
}
