package com.yam.core.util.plugin

import android.content.Intent
import android.os.AsyncTask
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.yam.core.view.fragment.YFragment
import com.yam.core.view.fragment.YWebFragment
import org.json.JSONObject

abstract class YPlugin : AsyncTask<JSONObject, Void, Void>(), YPluginInterface {
    lateinit var id: String
    lateinit var param: JSONObject

    var webView: WebView? = null

    lateinit var fragment: Fragment
    lateinit var activity: FragmentActivity

    lateinit var listener: CompleteListener

    fun setPlugin(fragment: Fragment, listener: CompleteListener) {
        this.fragment = fragment
        this.activity = fragment.activity!!
        this.listener = listener

        if(fragment is YWebFragment){
            this.webView = (fragment as YWebFragment).yWebView
        }
    }

    override fun doInBackground(vararg jsonObjects: JSONObject): Void? {
        id = jsonObjects[0].getString("id")
        param = jsonObjects[0].getJSONObject("param")
        executePlugin()
        return null
    }

    protected abstract fun executePlugin()
    fun startActivityForResult(intent: Intent, requestCode: Int){
        (fragment as YFragment).startActivityForResultFromPlugin(this, intent, requestCode)
    }

    override fun onActivityResult(reqCode: Int, resCode: Int, intent: Intent?) {
    }

    override fun onRequestPermissionsResult(reqCode: Int, permissions: Array<String>, grantResults: Array<Int>) {
    }

}
