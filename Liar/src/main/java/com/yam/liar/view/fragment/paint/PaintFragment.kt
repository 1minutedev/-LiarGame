package com.yam.liar.view.fragment.paint

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.yam.core.util.RUtil
import com.yam.core.util.plugin.CompleteListener
import com.yam.core.view.fragment.YFragment
import com.yam.liar.R
import kotlinx.android.synthetic.main.fragment_paint.*
import org.json.JSONObject

class PaintFragment : YFragment(), PaintContract.View, View.OnClickListener, View.OnTouchListener {
    lateinit var paintPresenter: PaintPresenter

    var callback = ""
    var total = 3

    lateinit var viewPager : ViewPager2
    lateinit var btnNext: Button
    lateinit var ibClear: ImageButton
    lateinit var btnClear: LinearLayout

    lateinit var tvSwipe: TextView

    open lateinit var listener: CompleteListener

    lateinit var mAdapter: PaintPagerRecyclerAdapter

    companion object{
        var isReadOnly = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        wrapper = inflater.inflate(RUtil.getLayoutR(activity!!.applicationContext, "fragment_paint"), null)
        return wrapper
    }

    override fun onInit() {
        isReadOnly = false

        paintPresenter = PaintPresenter()
        paintPresenter.setView(this)

        callback = arguments!!.getString("callback", "")
        total = arguments!!.getInt("total", 3)

        btnNext = btn_next
        btnNext.setOnClickListener(this)

        btnClear = btn_clear
        btnClear.setOnClickListener(this)
        btnClear.setOnTouchListener(this)

        ibClear = ib_clear
        ibClear.setOnClickListener(this)
        ibClear.setOnTouchListener(this)

        tvSwipe = tv_swipe_message

        viewPager = vp_paint

        mAdapter = PaintPagerRecyclerAdapter(activity!!.applicationContext, total)
        mAdapter.fragment = this

        viewPager.adapter = mAdapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.isUserInputEnabled = false
    }

    override fun onClick(view: View?) {
        var currentIndex = viewPager.currentItem

        when(view){
            btnNext -> {
                AlertDialog.Builder(activity!!)
                    .setMessage(resources.getString(R.string.txt_alert_message_next))
                    .setCancelable(false)
                    .setPositiveButton(resources.getString(R.string.txt_ok),
                        DialogInterface.OnClickListener {
                            dialogInterface, i ->
                                if(currentIndex < total-1){
                                    viewPager.setCurrentItem(++currentIndex, true)
                                } else {
                                    isReadOnly = true

                                    hideButton()

                                    viewPager.setCurrentItem(0, true)
                                    viewPager.isUserInputEnabled = true

                                    Toast.makeText(activity, resources.getString(R.string.txt_toast_readonly), Toast.LENGTH_SHORT).show()

                                    startAnim()
                                }
                        })
                    .setNegativeButton(resources.getString(R.string.txt_cancle), DialogInterface.OnClickListener {
                            dialogInterface, i -> {}
                    })
                    .create().show()
            }
            ibClear, btnClear -> {
                mAdapter.holders.get(currentIndex).mPaintView.canvasClear()
            }
        }
    }

    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
        if(view == ibClear || view == btnClear){
            when(motionEvent!!.action){
                MotionEvent.ACTION_DOWN -> {
                    btnClear.background = ContextCompat.getDrawable(activity!!, R.drawable.cir_shadow_sel)
                    return false
                }
                MotionEvent.ACTION_UP -> {
                    btnClear.background = ContextCompat.getDrawable(activity!!, R.drawable.cir_shadow)
                    return false
                }
            }
        }
        return true
    }

    fun hideButton(){
        if(btnNext.visibility == View.VISIBLE){
            btnNext.visibility = View.GONE
        }

        if(btnClear.visibility == View.VISIBLE){
            btnClear.visibility = View.GONE
        }

        for(holder in mAdapter.holders){
            holder.hideButton()
        }
    }

    fun startAnim(){
        if(tvSwipe.visibility == View.GONE){
            tvSwipe.visibility = View.VISIBLE
        }

        var fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.setInterpolator(DecelerateInterpolator())
        fadeIn.duration = 2000
        fadeIn.repeatCount = Animation.INFINITE

        var fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.setInterpolator(DecelerateInterpolator())
        fadeOut.duration = 2000
        fadeOut.repeatCount = Animation.INFINITE

        var anim = AnimationSet(true)
        anim.addAnimation(fadeOut)
        anim.addAnimation(fadeIn)

        tvSwipe.animation = anim
    }

    fun sendCallback(resultData: JSONObject){
        listener.sendCallback(callback, resultData)
    }

}