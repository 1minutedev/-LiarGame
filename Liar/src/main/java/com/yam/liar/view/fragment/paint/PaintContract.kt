package com.yam.liar.view.fragment.paint

interface PaintContract {
    interface View {

    }

    interface Presenter {
        fun setView(view: PaintContract.View)
    }

}