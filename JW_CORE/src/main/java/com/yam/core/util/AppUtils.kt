package com.yam.core.util

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.yam.core.application.YApplication
import com.yam.core.view.fragment.YFragment

class AppUtils {
    companion object {
        fun restartApp(activity: FragmentActivity){
            var fragmentManager = activity.supportFragmentManager
            var transaction = fragmentManager.beginTransaction()

            while(YApplication.fragmentList.size > 0){
                transaction.remove(YApplication.fragmentList.get(YApplication.fragmentList.size - 1))
                YApplication.fragmentList.removeAt(YApplication.fragmentList.size - 1)
            }

            var fragment = YApplication.LAUNCHER_FRAGMENT.newInstance() as YFragment
            var arguments = Bundle()

            fragment.arguments = arguments

            activity.supportFragmentManager
                .beginTransaction()
                .replace(RUtil.getIdR(activity.applicationContext, "contents"), fragment, "center")
                .commitAllowingStateLoss()
        }
    }
}