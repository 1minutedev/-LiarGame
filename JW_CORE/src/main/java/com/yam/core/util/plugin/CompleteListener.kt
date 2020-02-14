package com.yam.core.util.plugin

import org.json.JSONObject

interface CompleteListener {
    fun sendCallback(callback: String, resultData: JSONObject)
}