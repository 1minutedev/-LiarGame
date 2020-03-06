package com.yam.liar.view.fragment.paint

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2
import com.yam.core.util.RUtil
import com.yam.core.view.fragment.YFragment
import kotlinx.android.synthetic.main.fragment_paint.*

class PaintFragment : YFragment(), PaintContract.View, View.OnClickListener {
    lateinit var paintPresenter: PaintPresenter

    var callback = ""
    var total = 3

    lateinit var viewPager : ViewPager2
    lateinit var btnNext: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        wrapper = inflater.inflate(RUtil.getLayoutR(activity!!.applicationContext, "fragment_paint"), null)
        return wrapper
    }

    override fun onInit() {
        paintPresenter = PaintPresenter()
        paintPresenter.setView(this)

        callback = arguments!!.getString("callback", "")
        total = arguments!!.getInt("total", 3)

        btnNext = btn_next
        btnNext.setOnClickListener(this)

        viewPager = vp_paint

        var pagerRecyclerAdapter = PaintPagerRecyclerAdapter(activity!!.applicationContext, total)
        pagerRecyclerAdapter.fragment = this

        viewPager.adapter = pagerRecyclerAdapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.isUserInputEnabled = false
    }

    override fun onClick(view: View?) {
        when(view){
            btnNext -> {
                var currentIndex = viewPager.currentItem
                if(currentIndex < total){
                    viewPager.setCurrentItem(++currentIndex, true)
                } else {

                }
            }
        }
    }
}