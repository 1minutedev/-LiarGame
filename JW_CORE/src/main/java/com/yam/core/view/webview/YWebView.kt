package com.yam.core.view.webview

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView

open class YWebView : WebView {

    @SuppressLint("WrongConstant")
    constructor(context: Context) : super(context){
        clearCache(true)
        isFocusable = true
        scrollBarStyle = Context.BIND_AUTO_CREATE

        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
            setWebContentsDebuggingEnabled(true)
        }

        settings.defaultTextEncodingName = "UTF-8"
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.setGeolocationEnabled(true)
        settings.databaseEnabled = true
        settings.builtInZoomControls = true
        settings.useWideViewPort = true
        settings.setAppCacheEnabled(false)
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH)

        settings.loadWithOverviewMode = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL

        if (Build.VERSION.SDK_INT >= 14) {
            settings.textZoom = 100
        } else {
            settings.textSize = WebSettings.TextSize.NORMAL
        }

        if (Build.VERSION.SDK_INT >= 16) {
            settings.allowUniversalAccessFromFileURLs = true
        }

        settings.setSupportZoom(true)

        if (Build.VERSION.SDK_INT >= 11) {
            settings.displayZoomControls = false
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.setAcceptThirdPartyCookies(this, true)
        }

        settings.setAllowFileAccess(true)
        settings.setAllowContentAccess(true)
        settings.setAllowFileAccessFromFileURLs(true)
        settings.setAllowUniversalAccessFromFileURLs(true)
    }

}