package com.yam.liar.plugin

import android.util.Log
import android.webkit.ValueCallback
import com.yam.core.util.plugin.YPlugin
import org.json.JSONObject

class TestPlugin : YPlugin() {
    var callback: String = ""

    lateinit var result: JSONObject

    override fun executePlugin() {
        result = JSONObject()

        if (param.has("callback")) {
            callback = param.getString("callback");
        }

        result.put("result", true)

        resultCallback(callback, result)
    }
}