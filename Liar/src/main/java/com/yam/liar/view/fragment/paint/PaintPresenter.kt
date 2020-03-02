package com.yam.liar.view.fragment.paint

import java.util.*

class PaintPresenter : PaintContract.Presenter {
    private var view: PaintContract.View? = null

    override fun setView(view: PaintContract.View) {
        this.view = view
    }

}