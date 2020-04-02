package com.yam.core.view.fragment

import android.os.Build
import android.util.Log
import android.webkit.ValueCallback
import com.yam.core.view.webview.YWebView

open class YWebFragment : YFragment() {
    var yWebView: YWebView? = null
    override fun onInit() {
    }

    open fun backButtonEvent(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            yWebView!!.loadUrl("javascript:backButton()")
        } else {
            yWebView!!.evaluateJavascript("javascript:backButton()", ValueCallback { })
        }
        Log.d("YWebFragment", "javascript:backButton()")
    }
}
