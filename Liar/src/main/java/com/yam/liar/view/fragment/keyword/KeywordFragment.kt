package com.yam.liar.view.fragment.keyword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yam.core.util.RUtil
import com.yam.core.view.fragment.YFragment

class KeywordFragment : YFragment(), KeywordContract.View {
    lateinit var keywordPresenter: KeywordPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        wrapper = inflater.inflate(RUtil.getLayoutR(activity!!.applicationContext, "fragment_keyword" ), null)
        return wrapper
    }

    override fun onInit() {
        keywordPresenter = KeywordPresenter()
        keywordPresenter.setView(this)
    }

}