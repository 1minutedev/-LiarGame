package com.yam.core.util

import android.os.Environment
import android.text.TextUtils
import com.yam.core.application.YApplication

class Config {
    companion object{
        val MODE_ASSETS = "assets"
        val MODE_ABSOLUTE = "absolute"
        val MODE_EXTERNAL = "external"

        val contentsPath = "contents/index.html"

        fun getUrl() : String{
            when(YApplication.contentsMode){
                MODE_ASSETS -> {
                    return "file:///android_asset/$contentsPath"
                }
                MODE_ABSOLUTE -> {
                    return YApplication.contentsUrl
                }
                MODE_EXTERNAL -> {
                    var path = "file:///"
                    var externalPath = Environment.getExternalStorageDirectory().absolutePath;

                    if(!externalPath.endsWith("/")){
                        externalPath += "/"
                    }

                    path += externalPath

                    if(!TextUtils.isEmpty(YApplication.contentsExternalDirectoryPath)){
                        if(YApplication.contentsExternalDirectoryPath.startsWith("/")){
                            path += YApplication.contentsExternalDirectoryPath.substring(1)
                        } else {
                            path += YApplication.contentsExternalDirectoryPath
                        }
                    }

                    if(!path.endsWith("/")){
                        path += "/"
                    }

                    path += contentsPath

                    return path
                }
            }

            return ""
        }

        fun getFilePath() : String{
            return ""
        }
    }
}