package com.yam.liar.plugin

import android.os.Bundle
import android.text.TextUtils
import com.yam.core.util.plugin.YPlugin
import com.yam.core.view.fragment.YFragment
import com.yam.liar.view.fragment.keyword.KeywordFragment
import org.json.JSONObject

class ShowKeywordPlugin : YPlugin() {
    val TAG = ShowKeywordPlugin::class.simpleName

    var callback: String = ""
    lateinit var result: JSONObject

    override fun executePlugin() {
        result = JSONObject()

        var category = ""
        var keyword = ""
        var total = 3

        if(param.has("callback")){
            callback = param.getString("callback")
        }

        if(param.has("category")){
            category = param.getString("category")
        }

        if(param.has("keyword")){
            keyword = param.getString("keyword")
        }

        if(param.has("total")){
            total = param.getInt("total")
        }

        if(TextUtils.isEmpty(category) || TextUtils.isEmpty(keyword)){
            result.put("result", false)
            result.put("err_msg", "Parameter does not have category or keyword")
            listener.sendCallback(callback, result)
            return
        }

        var keywordFragment = KeywordFragment()

        var arguments = Bundle()
        arguments.putString("callback", callback)
        arguments.putString("category", category)
        arguments.putString("keyword", keyword)
        arguments.putInt("total", total)

        keywordFragment.arguments = arguments

        keywordFragment.setListener(listener)

        (fragment as YFragment).moveToFragment(keywordFragment, "fade")
    }

}