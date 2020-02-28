package com.yam.liar.view.fragment.keyword

import java.util.*

class KeywordPresenter : KeywordContract.Presenter {
    private var view: KeywordContract.View? = null

    override fun setView(view: KeywordContract.View) {
        this.view = view
    }

    override fun getLiar(total: Int): Int {
        return Random().nextInt(total) + 1
    }
}