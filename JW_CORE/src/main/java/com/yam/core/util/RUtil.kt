package com.yam.core.util

import android.content.Context

class RUtil {
    companion object{
        fun getLayoutR(context: Context, layoutName: String): Int {
            var packageName = context.packageName
            return context.resources.getIdentifier(layoutName, "layout", packageName)
        }
        fun getIdR(context: Context, idName: String): Int {
            var packageName = context.packageName
            return context.resources.getIdentifier(idName, "id", packageName)
        }
        fun getR(context: Context, defType: String, name: String): Int {
            var packageName = context.packageName
            return context.resources.getIdentifier(name, defType, packageName)
        }
    }
}