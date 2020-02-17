package com.yam.core.view.webview

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.KeyEvent
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView

class YWebChromeClient : WebChromeClient {
    lateinit var context: Context
    lateinit var webView: WebView

    constructor(context: Context, webView: WebView) : super(){
        this.context = context
        this.webView = webView
    }


    override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
        val dlg = AlertDialog.Builder(context)
        dlg.setMessage(message)
        dlg.setTitle("Alert")
        //Don't let alerts break the back button
        dlg.setCancelable(true)
        dlg.setPositiveButton(context.resources.getString(android.R.string.ok), DialogInterface.OnClickListener { dialogInterface, i -> result!!.confirm() })
        dlg.setOnCancelListener { result!!.cancel() }
        dlg.setOnKeyListener { dialog, keyCode, event ->
            //DO NOTHING
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                result!!.confirm()
                false
            } else
                true
        }
        dlg.create()
        dlg.show()
        return true
    }

}