package com.yam.liar.view.fragment.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.LinearLayout
import com.yam.core.util.RUtil
import com.yam.core.util.plugin.YBridge
import com.yam.core.view.fragment.YWebFragment
import com.yam.core.view.webview.YWebChromeClient
import com.yam.core.view.webview.YWebView
import com.yam.core.view.webview.YWebViewClient

open class WebFragment : YWebFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        wrapper = inflater.inflate(RUtil.getLayoutR(activity!!.applicationContext, "fragment_web"), null)
        return wrapper
    }

    override fun onInit() {
        var mainView = wrapper.findViewById(RUtil.getIdR(activity!!.applicationContext, "main_view")) as? LinearLayout
        var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        yWebView = YWebView(activity!!.applicationContext)
        yWebView.webViewClient = YWebViewClient()
        yWebView.webChromeClient = YWebChromeClient(activity!!, yWebView)

        val settings = yWebView.getSettings()
        settings.setJavaScriptEnabled(true)
        settings.setDomStorageEnabled(true)
        settings.setGeolocationEnabled(true)
        settings.setDatabaseEnabled(true)
        settings.setBuiltInZoomControls(true)
        settings.setUseWideViewPort(true)
        settings.setAppCacheEnabled(false)
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE)
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH)

        settings.setLoadWithOverviewMode(true)
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL)
        settings.setAllowUniversalAccessFromFileURLs(true)
        settings.setSupportZoom(true)
        settings.setTextZoom(100)
        settings.setDisplayZoomControls(false)

        yWebView.addJavascriptInterface(YBridge(this, yWebView), "YBridge")

        mainView?.addView(yWebView, params)

        yWebView.loadUrl("http://192.168.30.46:8080/biz3/contents/LGN/html/TST.html")
    }
}