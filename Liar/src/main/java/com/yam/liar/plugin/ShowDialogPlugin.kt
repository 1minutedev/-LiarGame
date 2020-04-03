package com.yam.liar.plugin

import com.yam.core.util.plugin.YPlugin
import com.yam.core.view.fragment.YWebFragment
import com.yam.liar.view.dialog.BackbuttonDialog
import org.json.JSONObject

class ShowDialogPlugin : YPlugin() {
    val TAG = ShowDialogPlugin::class.simpleName

    var callback: String = ""
    lateinit var result: JSONObject

    lateinit var dialog : BackbuttonDialog

    override fun executePlugin() {
        var type = ""
        var title = ""
        var btnName1 = ""
        var btnName2 = ""
        var btnName3 = ""

        if(param.has("callback")){
            callback = param.getString("callback")
        }

        if(param.has("type")){
            type = param.getString("type")
        }

        if(param.has("title")){
            title = param.getString("title")
        }

        if(param.has("btnName1")){
            btnName1 = param.getString("btnName1")
        }

        if(param.has("btnName2")){
            btnName2 = param.getString("btnName2")
        }

        if(param.has("btnName3")){
            btnName3 = param.getString("btnName3")
        }

        activity.runOnUiThread( Runnable {
            dialog = BackbuttonDialog(activity, this)
            dialog.create()
            dialog.setBtn1(btnName1)
            dialog.setBtn2(btnName2)
            dialog.setBtn3(btnName3)
            dialog.show()
        })
    }

    fun sendCallback(clicked: Int){
        var result = JSONObject()

        result.put("result", true)
        result.put("clicked", clicked)

        (fragment as YWebFragment).yWebView!!.loadUrl("javascript:" + callback + "(" + result.toString() + ")")
    }
}