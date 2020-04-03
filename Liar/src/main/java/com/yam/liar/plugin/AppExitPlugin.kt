package com.yam.liar.plugin

import android.util.Log
import com.yam.core.util.Config
import com.yam.core.util.plugin.YPlugin
import com.yam.liar.view.activity.MainFragmentActivity
import org.json.JSONObject

class AppExitPlugin : YPlugin() {
    val TAG = AppExitPlugin::class.simpleName

    var callback: String = ""
    lateinit var result: JSONObject

    override fun executePlugin() {
        var type = ""

        if(param.has("callback")){
            callback = param.getString("callback")
        }

        if(param.has("type")){
            type = param.getString("type")
        }

        if(type.equals("restart")){
            activity.runOnUiThread {
                Log.e("wonmin", "Config.getUrl() : " + Config.getUrl())
                webView!!.loadUrl(Config.getUrl())
            }
        } else {
            (activity as MainFragmentActivity).exit()
        }
    }

}