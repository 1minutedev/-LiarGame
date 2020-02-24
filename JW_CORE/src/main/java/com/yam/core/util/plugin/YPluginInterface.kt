package com.yam.core.util.plugin

import android.content.Intent

interface YPluginInterface {
    fun onActivityResult(reqCode: Int, resCode: Int, intent: Intent?)
    fun onRequestPermissionsResult(reqCode: Int, permissions: Array<String>, grantResults: Array<Int>)
}