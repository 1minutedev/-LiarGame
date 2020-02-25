package com.yam.core.util.plugin

import android.content.res.XmlResourceParser
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import org.json.JSONArray
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.indices
import kotlin.collections.set

class YBridge {
    private var activity: FragmentActivity? = null
    private var fragment: Fragment? = null
    private var webView: WebView? = null

    private var pluginList: JSONArray? = null
    private var interfaces: HashMap<String, String>? = null

    internal val TAG = YBridge::class.simpleName
    internal val interfaceFileName = "plugin_list"
    internal val interfaceFileExt = "xml"

    companion object {
        var yBridge : YBridge? = null
        var classes: HashMap<String, Class<*>>? = null

        fun getInstance() : YBridge {
            if(yBridge == null){
                yBridge = YBridge()
                classes = HashMap<String, Class<*>>()
            }

            return yBridge!!
        }
    }

    open fun setFragment(fragment: Fragment){
        this.fragment = fragment
        setActivity(fragment.activity!!)
    }
    open fun setWebView(webView: WebView?){
        this.webView = webView
    }
    open fun setActivity(activity: FragmentActivity){
        this.activity = activity
    }


    open fun callPluginFromApp(id: String, data: JSONObject, completeListener: CompleteListener){
        executePlugin(id, data, completeListener)
    }

    @JavascriptInterface
    fun callPlugin(paramString: String) {
        try {
            val data = JSONObject(paramString)
            var id = ""

            if (data.has("id")) {
                id = data.getString("id")
            }

            if (interfaces == null) {
                interfaces = getInterfaces(interfaceFileName, interfaceFileExt)
            }

            var completeListener = object : CompleteListener {
                override fun sendCallback(callback: String, resultData: JSONObject) {
                    activity!!.runOnUiThread { webView!!.loadUrl("javascript:$callback($resultData)") }
                }
            }

            executePlugin(id, data, completeListener)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun executePlugin(id: String, data: JSONObject, completeListener: CompleteListener){
        var plugin: YPlugin? = null

        if(checkInterface(id)){
            registKeyAndBindingClass(id, interfaces!!.get(id)!!)
            plugin = getModel(id)!!.newInstance() as YPlugin
            plugin.setPlugin(fragment!!, completeListener)
            plugin.execute(data)
        } else {
            val error = JSONObject()
            error.put("result", false)
            error.put("errCode", 44444)
            error.put("errMessage", "$id plugin not found")

            var param: JSONObject? = null
            var callback = ""

            if (data.has("param")) {
                param = data.getJSONObject("param")
            }

            if (param!!.has("callback")) {
                callback = param.getString("callback")
            }

            completeListener.sendCallback(callback, error)
        }
    }

    fun checkInterface(key: String): Boolean {
        if (interfaces == null) {
            interfaces = getInterfaces(interfaceFileName, interfaceFileExt)
        }

        return interfaces!!.containsKey(key)
    }

    fun registKeyAndBindingClass(aKey: String, className: String) {
        if (isKeyAlreadyRegisted(aKey)) {
            Log.e(TAG, "key $aKey is already exist, model doesn't insert.")
        } else {
            if (isValueAlreadyRegisted(className)) {
                addKeyAlreadyBindingClass(aKey, className)   //이미 있는데 할 필요가 있나??

            } else {
                insertNewKeyAndModel(aKey, className)
            }

        }
    }

    private fun isKeyAlreadyRegisted(aKey: String): Boolean {
        val allKeys = ArrayList<String>(classes!!.keys)
        return allKeys.contains(aKey)
    }

    private fun isValueAlreadyRegisted(className: String): Boolean {
        val allObjs = ArrayList<Class<*>>(classes!!.values)

        var isValueRegisted = false

        for (i in allObjs.indices) {

            if (allObjs.javaClass.getName() == className) {

                isValueRegisted = true
                break
            }
        }

        return isValueRegisted
    }

    private fun addKeyAlreadyBindingClass(aKey: String, className: String) {
        val obj = getExistModel(className)
        setKeyAndModel(aKey, obj)
    }

    private fun getExistModel(className: String): Class<*>? {
        val allObjs = ArrayList<Class<*>>(classes!!.values)
        var model: Class<*>? = null

        for (obj in allObjs) {

            if (obj.javaClass.getName() == className) {

                model = obj
                break
            }
        }

        return model
    }

    private fun setKeyAndModel(aKey: String, model: Class<*>?) {
        classes!!.put(aKey, model!!)
    }

    private fun insertNewKeyAndModel(aKey: String, className: String) {
        var obj: Class<*>? = null
        try {
            obj = createModel(className)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        setKeyAndModel(aKey, obj)
    }

    @Throws(
        ClassNotFoundException::class,
        InstantiationException::class,
        IllegalAccessException::class
    )
    private fun createModel(className: String?): Class<*>? {
        var obj: Class<*>? = null
        if (className != null) {
            val c = Class.forName(className)
            obj = c //(Class) c.newInstance();
        }
        return obj
    }

    fun getModel(aKey: String): Class<*>? {
        return classes!!.get(aKey)
    }

    fun getInterfaces(fileName: String, ext: String): HashMap<String, String> {
        if (pluginList == null) {
            pluginList = JSONArray()
        }

        val interfaces = HashMap<String, String>()

        var context = activity!!.applicationContext

        val id = context!!.resources.getIdentifier(fileName, ext, context.packageName)
        val xml = context.resources.getXml(id)
        var eventType = -1
        var key = ""
        var value = ""
        while (eventType != XmlResourceParser.END_DOCUMENT) {
            if (eventType == XmlResourceParser.START_TAG) {
                val strNode = xml.name
                //This is for the old scheme
                if (strNode == "plugin") {
                    key = xml.getAttributeValue(null, "key")
                    value = xml.getAttributeValue(null, "value")

                    interfaces[key] = value
                    pluginList!!.put(key)
                }
            } else if (eventType == XmlResourceParser.END_TAG) {
                val strNode = xml.name
                if (strNode == "feature") {
                }
            }
            try {
                eventType = xml.next()
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return interfaces
    }

}