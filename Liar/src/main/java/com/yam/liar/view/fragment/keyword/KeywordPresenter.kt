package com.yam.liar.view.fragment.keyword

class KeywordPresenter : KeywordContract.Presenter {
    private var view: KeywordContract.View? = null

    override fun setView(view: KeywordContract.View) {
        this.view = view
    }

}