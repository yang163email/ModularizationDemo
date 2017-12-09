package com.yan.modularization.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yan.modularization.R
import com.yan.modularization.base.BaseFragment

/**
 *  @author      : 楠GG
 *  @date        : 2017/12/9 11:45
 *  @description : 消息fragment
 */
class MessageFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_message_layout, container, false)
    }
}