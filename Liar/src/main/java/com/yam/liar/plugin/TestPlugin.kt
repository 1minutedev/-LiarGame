package com.yam.liar.plugin

import android.util.Log
import com.yam.core.util.plugin.YPlugin
import org.json.JSONObject

class TestPlugin : YPlugin(){
    override fun executePlugin(data: JSONObject) {
        Log.e("wonmin", "data : $data");
    }
}