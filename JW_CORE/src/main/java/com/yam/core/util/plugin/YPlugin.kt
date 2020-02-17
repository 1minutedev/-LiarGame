package com.yam.core.util.plugin

import android.os.AsyncTask
import android.util.Log
import android.webkit.WebView
import androidx.fragment.app.Fragment

import org.json.JSONObject

import androidx.fragment.app.FragmentActivity

abstract class YPlugin : AsyncTask<JSONObject, Void, Void>() {
    lateinit var id: String
    lateinit var param: JSONObject

    lateinit var webView: WebView
    lateinit var fragment: Fragment
    lateinit var activity: FragmentActivity

    lateinit var listener: CompleteListener

    fun setPlugin(webView: WebView, fragment: Fragment, listener: CompleteListener) {
        this.webView = webView
        this.fragment = fragment
        this.activity = fragment.activity!!
        this.listener = listener
    }

    override fun doInBackground(vararg jsonObjects: JSONObject): Void? {
        id = jsonObjects[0].getString("id")
        param = jsonObjects[0].getJSONObject("param")
        executePlugin()
        return null
    }

    protected abstract fun executePlugin()

    fun resultCallback(callback: String, data: JSONObject){
        activity.runOnUiThread {
            Log.e(id, "javascript:$callback($data)")
            webView.evaluateJavascript("javascript:$callback($data)", null)
        }
    }
}
