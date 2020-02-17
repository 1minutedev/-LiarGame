package com.yam.liar.view.fragment.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yam.core.util.RUtil
import com.yam.core.view.fragment.YFragment

class SplashFragment : YFragment(), SplashContract.View {
    lateinit var splashPresenter : SplashPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        wrapper = inflater.inflate(RUtil.getLayoutR(activity!!.applicationContext, "fragment_splash" ), null)
        return wrapper
    }

    override fun onInit() {
        splashPresenter = SplashPresenter()
        splashPresenter.setView(this)

        splashPresenter.loadData()
    }

    override fun loadComplete() {
        Handler().postDelayed(Runnable {
            splashPresenter.openWeb()
        }, 2000)
    }
}