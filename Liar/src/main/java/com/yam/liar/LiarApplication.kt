package com.yam.liar

import com.yam.core.application.YApplication
import com.yam.liar.view.fragment.splash.SplashFragment

class LiarApplication : YApplication() {

    override fun onCreate() {
        super.onCreate()

        buildMode = resources.getString(R.string.buildMode)

        if(buildMode.equals("release")){
            useSettingView = false
        }

        LAUNCHER_FRAGMENT = SplashFragment::class.java
    }

}