package com.yam.liar.view.fragment.splash

interface SplashContract {

    interface View {
        fun loadComplete()
    }

    interface Presenter {
        fun setView(view: View)
        fun loadData()
        fun moveToMain()
    }
}