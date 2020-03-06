package com.yam.liar.plugin

import android.os.Bundle
import com.yam.core.util.plugin.YPlugin
import com.yam.core.view.fragment.YFragment
import com.yam.liar.view.fragment.paint.PaintFragment
import org.json.JSONObject

class OpenPaintPlugin : YPlugin(){
    val TAG = OpenPaintPlugin::class.simpleName

    var callback: String = ""
    lateinit var result: JSONObject

    override fun executePlugin() {
        result = JSONObject()

        var total = 3

        if(param.has("callback")){
            callback = param.getString("callback")
        }
        if(param.has("total")){
            total = param.getInt("total")
        }

        var paintFragment = PaintFragment()

        var arguments = Bundle()
        arguments.putString("callback", callback)
        arguments.putInt("total", total)

        paintFragment.arguments = arguments
        paintFragment.listener = listener

        (fragment as YFragment).moveToFragment(paintFragment, "fade")
    }

}