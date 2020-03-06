package com.yam.liar.view.fragment.paint

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.yam.core.util.RUtil
import com.yam.core.util.plugin.CompleteListener
import com.yam.core.view.fragment.YFragment
import com.yam.liar.R
import kotlinx.android.synthetic.main.fragment_paint.*
import org.json.JSONObject

class PaintFragment : YFragment(), PaintContract.View, View.OnClickListener {
    lateinit var paintPresenter: PaintPresenter

    var callback = ""
    var total = 3

    lateinit var viewPager : ViewPager2
    lateinit var btnNext: Button

    open lateinit var listener: CompleteListener

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

                AlertDialog.Builder(activity!!)
                    .setMessage(resources.getString(R.string.txt_alert_message_next))
                    .setCancelable(false)
                    .setPositiveButton(resources.getString(R.string.txt_ok),
                        DialogInterface.OnClickListener {
                            dialogInterface, i ->
                                if(currentIndex < total-1){
                                    viewPager.setCurrentItem(++currentIndex, true)
                                } else {
                                    viewPager.setCurrentItem(0, true)
                                    viewPager.isUserInputEnabled = true
                                    btnNext.visibility = View.GONE
                                    Toast.makeText(activity, resources.getString(R.string.txt_toast_readonly), Toast.LENGTH_SHORT).show()
                                }
                        })
                    .setNegativeButton(resources.getString(R.string.txt_cancle), DialogInterface.OnClickListener {
                            dialogInterface, i -> {}
                    })
                    .create().show()
            }
        }
    }

    fun sendCallback(resultData: JSONObject){
        listener.sendCallback(callback, resultData)
    }
}