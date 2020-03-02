package com.yam.liar.view.fragment.keyword

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.yam.core.util.RUtil
import com.yam.core.util.plugin.CompleteListener
import com.yam.core.view.fragment.YFragment
import com.yam.liar.view.activity.MainFragmentActivity
import kotlinx.android.synthetic.main.fragment_keyword.*
import org.json.JSONObject

class KeywordFragment : YFragment(), KeywordContract.View, View.OnClickListener {
    lateinit var keywordPresenter: KeywordPresenter

    var callback = ""

    var category = ""
    var keyword = ""
    var total = 3

    var currentNumber = 1
    var liarNumber = 0

    lateinit var tvSequence: TextView
    lateinit var tvCategory: TextView
    lateinit var tvKeyword: TextView
    lateinit var btnCheckKeyword: Button

    lateinit var llBlind: LinearLayout
    lateinit var llKeyword: LinearLayout
    lateinit var llLiar: LinearLayout

    lateinit var completeListener: CompleteListener

    open fun setListener(completeListener: CompleteListener){
        this.completeListener = completeListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        wrapper = inflater.inflate(
            RUtil.getLayoutR(activity!!.applicationContext, "fragment_keyword"),
            null
        )
        return wrapper
    }

    override fun onInit() {
        keywordPresenter = KeywordPresenter()
        keywordPresenter.setView(this)

        callback = arguments!!.getString("callback", "")

        category = arguments!!.getString("category", "")
        keyword = arguments!!.getString("keyword", "")
        total = arguments!!.getInt("total", 3)

        tvSequence = tv_seq
        tvSequence.setText(currentNumber.toString())

        tvCategory = tv_category
        tvCategory.setText(category)

        tvKeyword = tv_keyword
        tvKeyword.setText(keyword)

        btnCheckKeyword = btn_check_keyword
        btnCheckKeyword.setOnClickListener(this)

        llBlind = ll_blind
        llKeyword = ll_keyword
        llLiar = ll_liar

        liarNumber = keywordPresenter.getLiar(total)
    }

    override fun onClick(view: View?) {
        when (view!!) {
            btnCheckKeyword -> {
                if (currentNumber <= total) {
                    showKeyword()

                    Handler().postDelayed(Runnable {
                        currentNumber++

                        if(currentNumber > total){
                            sendCallback()
                        } else {
                            hideKeyword()
                            tvSequence.setText(currentNumber.toString())
                        }
                    }, 2000)
                }
            }
        }
    }

    fun showKeyword(){
        if (llBlind.visibility == View.VISIBLE) {
            llBlind.visibility = View.GONE
        }
        if (llKeyword.visibility == View.GONE) {
            llKeyword.visibility = View.VISIBLE
        }

        if (currentNumber == liarNumber) {
            if (tvKeyword.visibility == View.VISIBLE) {
                tvKeyword.visibility = View.GONE
            }
            if (llLiar.visibility == View.GONE) {
                llLiar.visibility = View.VISIBLE
            }
        }
    }

    fun hideKeyword(){
        if (llLiar.visibility == View.VISIBLE) {
            llLiar.visibility = View.GONE
        }
        if (tvKeyword.visibility == View.GONE) {
            tvKeyword.visibility = View.VISIBLE
        }

        if (llKeyword.visibility == View.VISIBLE) {
            llKeyword.visibility = View.GONE
        }
        if (llBlind.visibility == View.GONE) {
            llBlind.visibility = View.VISIBLE
        }
    }

    fun sendCallback(){
        var result = JSONObject()

        result.put("result", true)
        result.put("keyword", keyword)
        result.put("liar_num", liarNumber)

        (activity as MainFragmentActivity).onBackPressed()
        completeListener.sendCallback(callback, result)
    }
}