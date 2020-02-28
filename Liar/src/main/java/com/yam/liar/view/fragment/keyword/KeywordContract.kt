package com.yam.liar.view.fragment.keyword

interface KeywordContract {

    interface View {

    }

    interface Presenter {
        fun setView(view: KeywordContract.View)
        fun getLiar(total:Int) : Int
    }

}