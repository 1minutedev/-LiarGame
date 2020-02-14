package com.yam.liar.view.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yam.core.util.RUtil
import com.yam.core.view.fragment.YFragment

open class MainFragment : YFragment(), MainContract.View {
    lateinit var mainPresenter: MainPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        wrapper = inflater.inflate(RUtil.getLayoutR(activity!!.applicationContext, "fragment_main"), null)
        return wrapper
    }

    override fun onInit() {
        mainPresenter = MainPresenter()
        mainPresenter.setView(this)

        var data = arguments;
        var isLogin = data!!.getBoolean("isLogin", false)

        if (!isLogin) {
            mainPresenter.showLoginPage()
        } else {
            mainPresenter.initLayout()
        }
    }

}