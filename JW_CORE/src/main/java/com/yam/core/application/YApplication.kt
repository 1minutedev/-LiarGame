package com.yam.core.application

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.yam.core.util.Config
import com.yam.core.util.plugin.YBridge
import com.yam.core.view.fragment.YWebFragment

open class YApplication : Application() {

    companion object {
        /**
         * 빌드 모드
         */
        var buildMode = "release"

        /**
         * 세팅 화면 사용 여부
         */
        var useSettingView = true

        /**
         * 컨텐츠 모드
         */
        var contentsMode = Config.MODE_ASSETS

        /**
         * absolute mode 에서 사용할 url
         */
        var contentsUrl = ""

        /**
         * external mode 일 때, external 경로를 제외한 컨텐츠가 실제로 있는 나머지 경로
         */
        var contentsExternalDirectoryPath = ""

        /**
         * 프레그먼트 리스트
         */
        var fragmentList: ArrayList<Fragment> = ArrayList()

        /**
         * 스택 추가
         */
        fun addFragment(fragment: Fragment){
            fragmentList.add(fragment)
            YBridge.getInstance().setFragment(fragment)
            if(fragment is YWebFragment){
                YBridge.getInstance().setWebView((fragment as YWebFragment).yWebView)
            } else {
                YBridge.getInstance().setWebView(null)
            }
        }

        fun removeFragment(fragment: Fragment){
            fragmentList.remove(fragment)

            var showFragment = fragmentList.get(fragmentList.size - 1)

            YBridge.getInstance().setFragment(showFragment)

            if(showFragment is YWebFragment){
                YBridge.getInstance().setWebView((showFragment as YWebFragment).yWebView)
            } else {
                YBridge.getInstance().setWebView(null)
            }
        }

        lateinit var LAUNCHER_FRAGMENT: Class<*>
    }

    override fun onCreate() {
        super.onCreate()

        var sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE)
        contentsMode = sharedPreferences.getString("contents_mode", Config.MODE_ASSETS)!!

        contentsUrl = sharedPreferences.getString("contents_url", "")!!

        contentsExternalDirectoryPath = sharedPreferences.getString("contents_external_directory_path", "")!!
    }

}