package com.yam.liar

import com.yam.core.application.YApplication

class LiarApplication : YApplication() {

    override fun onCreate() {
        super.onCreate()

        buildMode = resources.getString(R.string.buildMode)

        if(buildMode.equals("release")){
            useSettingView = false
        }
    }

}