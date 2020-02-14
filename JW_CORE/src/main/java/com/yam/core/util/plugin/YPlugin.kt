package com.yam.core.util.plugin

import android.os.AsyncTask

import org.json.JSONObject

import androidx.fragment.app.FragmentActivity

abstract class YPlugin : AsyncTask<JSONObject, Void, Void>() {
    var activity: FragmentActivity? = null
    lateinit var listener: CompleteListener

    fun setPlugin(activity: FragmentActivity, listener: CompleteListener) {
        this.activity = activity
        this.listener = listener
    }

    override fun doInBackground(vararg jsonObjects: JSONObject): Void? {
        executePlugin(jsonObjects[0])
        return null
    }

    protected abstract fun executePlugin(data: JSONObject)
}
