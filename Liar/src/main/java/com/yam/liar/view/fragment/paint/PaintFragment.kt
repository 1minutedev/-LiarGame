package com.yam.liar.view.fragment.paint

import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.Shape
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.yam.core.util.RUtil
import com.yam.core.view.fragment.YFragment
import kotlinx.android.synthetic.main.fragment_paint.*

class PaintFragment : YFragment(), PaintContract.View {
    lateinit var paintPresenter: PaintPresenter

    var callback = ""
    var total = 3

    lateinit var viewPager : ViewPager2

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
        total = arguments!!.getInt("total", 3)

        viewPager = vp_paint

        viewPager.adapter = PaintPagerRecyclerAdapter(activity!!.applicationContext, total)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.isUserInputEnabled = false
    }

}