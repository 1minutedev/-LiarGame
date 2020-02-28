package com.yam.liar.plugin

import android.os.Bundle
import com.yam.core.util.plugin.YPlugin
import com.yam.core.view.fragment.YFragment
import com.yam.liar.view.fragment.keyword.KeywordFragment
import org.json.JSONObject

class ShowKeywordPlugin : YPlugin() {
    val TAG = ShowKeywordPlugin::class.simpleName

    var callback: String = ""
    lateinit var result: JSONObject

    override fun executePlugin() {
        var keyword = ""
        var cnt = 3

        if(param.has("callback")){
            callback = param.getString("callback")
        }

        if(param.has("keyword")){
            keyword = param.getString("keyword")
        }

        if(param.has("cnt")){
            cnt = param.getInt("cnt")
        }

        var keywordFragment = KeywordFragment()

        var arguments = Bundle()
        arguments.putString("callback", callback)
        arguments.putString("keyword", keyword)
        arguments.putInt("cnt", cnt)

        keywordFragment.arguments = arguments

        (fragment as YFragment).moveToFragment(keywordFragment, "top")
    }

}