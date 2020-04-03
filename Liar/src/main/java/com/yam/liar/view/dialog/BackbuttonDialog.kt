package com.yam.liar.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.yam.core.util.plugin.CompleteListener
import com.yam.liar.R
import com.yam.liar.plugin.ShowDialogPlugin
import kotlinx.android.synthetic.main.dialog_default.*
import org.json.JSONObject

class BackbuttonDialog : Dialog, View.OnClickListener {
    lateinit var tvBtn1: TextView
    lateinit var tvBtn2: TextView
    lateinit var tvBtn3: TextView

    lateinit var showDialogPlugin: ShowDialogPlugin

    lateinit var listener: CompleteListener

    var fromWeb = true
    constructor(context: Context, listener: CompleteListener) : super(context){
        fromWeb = false
        this.listener = listener
    }

    constructor(context: Context, showDialogPlugin: ShowDialogPlugin) : super(context){
        fromWeb = true
        this.showDialogPlugin = showDialogPlugin
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_default)

        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
        window!!.setDimAmount(0.5f)

        tvBtn1 = tv_btn1
        tvBtn2 = tv_btn2
        tvBtn3 = tv_btn3

        tvBtn1.setOnClickListener(this)
        tvBtn2.setOnClickListener(this)
        tvBtn3.setOnClickListener(this)
    }

    fun setBtn1(text:String) {
        if(!TextUtils.isEmpty(text)){
            if(tvBtn1.visibility == View.GONE){
                tvBtn1.visibility = View.VISIBLE
                tvBtn1.setText(text)
            }
        }
    }

    fun setBtn2(text:String) {
        if(!TextUtils.isEmpty(text)){
            if(tvBtn2.visibility == View.GONE){
                tvBtn2.visibility = View.VISIBLE
                tvBtn2.setText(text)
            }
        }
    }

    fun setBtn3(text:String) {
        if(!TextUtils.isEmpty(text)){
            if(tvBtn3.visibility == View.GONE){
                tvBtn3.visibility = View.VISIBLE
                tvBtn3.setText(text)
            }
        }
    }

    override fun onClick(view: View?) {
        var clicked = 0
        when (view!!) {
            tvBtn1 -> {
                clicked = 1
            }
            tvBtn2 -> {
                clicked = 2
            }
            tvBtn3 -> {
                clicked = 3
            }
        }

        if(fromWeb) {
            showDialogPlugin.sendCallback(clicked)
        } else {
            var result = JSONObject()
            result.put("clicked", clicked)
            listener.sendCallback("", result)
        }

        dismiss()
    }
}