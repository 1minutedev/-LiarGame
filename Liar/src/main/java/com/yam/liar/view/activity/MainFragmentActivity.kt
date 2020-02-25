package com.yam.liar.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import com.yam.core.application.YApplication
import com.yam.core.util.RUtil
import com.yam.core.view.activity.YFragmentActivity
import com.yam.liar.R
import com.yam.liar.view.fragment.splash.SplashFragment

class MainFragmentActivity : YFragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment = SplashFragment()
        var arguments = Bundle()

        fragment.arguments = arguments

        supportFragmentManager
            .beginTransaction()
            .replace(RUtil.getIdR(applicationContext, "contents"), fragment, "center")
            .commitAllowingStateLoss()

//        YApplication.addFragment(fragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}
