package com.yam.core.application

import android.app.Application
import androidx.fragment.app.Fragment

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
        var contentsMode = "assets"

        /**
         * 프레그먼트 리스트
         */
        var fragmentList: ArrayList<Fragment> = ArrayList()

        /**
         * 스택 추가
         */
        fun addFragment(fragment: Fragment){
            fragmentList.add(fragment)
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

}