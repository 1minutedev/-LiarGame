package com.yam.liar.view.fragment.paint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yam.core.util.RUtil
import com.yam.core.view.fragment.YFragment

class PaintFragment : YFragment(), PaintContract.View {
    lateinit var paintPresenter: PaintPresenter

    var callback = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wrapper = inflater.inflate(
            RUtil.getLayoutR(activity!!.applicationContext, "fragment_paint"),
            null
        )
        return wrapper
    }

    override fun onInit() {
        paintPresenter = PaintPresenter()
        paintPresenter.setView(this)

        callback = arguments!!.getString("callback", "")
    }

}