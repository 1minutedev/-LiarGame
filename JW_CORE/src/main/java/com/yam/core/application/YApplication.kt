package com.yam.core.application

import android.app.Application
import androidx.fragment.app.Fragment

open class YApplication : Application() {

    companion object {
        var buildMode = "release"
        var isSettingMode = true
        var fragmentList: ArrayList<Fragment> = ArrayList()

        fun addFragment(fragment: Fragment){
            YApplication.fragmentList.add(fragment)
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

}