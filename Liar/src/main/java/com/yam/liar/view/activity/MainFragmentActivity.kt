package com.yam.liar.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.yam.core.application.YApplication
import com.yam.core.util.RUtil
import com.yam.core.util.plugin.CompleteListener
import com.yam.core.util.plugin.YBridge
import com.yam.core.view.activity.YFragmentActivity
import com.yam.liar.R
import com.yam.liar.view.dialog.BackbuttonDialog
import com.yam.liar.view.fragment.keyword.KeywordFragment
import com.yam.liar.view.fragment.paint.PaintFragment
import com.yam.liar.view.fragment.splash.SplashFragment
import org.json.JSONObject

class MainFragmentActivity : YFragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var fragment = SplashFragment()
        var arguments = Bundle()

        fragment.arguments = arguments

        supportFragmentManager
            .beginTransaction()
            .replace(RUtil.getIdR(applicationContext, "contents"), fragment, "center")
            .commitAllowingStateLoss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onBackPressed() {
        var currentFragment = YApplication.fragmentList.get(YApplication.fragmentList.size - 1)
        if(currentFragment is KeywordFragment || currentFragment is PaintFragment){
            var dialog = BackbuttonDialog(this, object : CompleteListener {
                override fun sendCallback(callback: String, resultData: JSONObject) {
                    var listener: CompleteListener? = null
                    var callback = ""

                    if(currentFragment is KeywordFragment){
                        listener = (currentFragment as KeywordFragment).completeListener
                        callback = (currentFragment as KeywordFragment).callback
                    } else if(currentFragment is PaintFragment){
                        listener = (currentFragment as PaintFragment).listener
                        callback = (currentFragment as PaintFragment).callback
                    }

                    if(resultData.has("clicked")){
                        var clicked = resultData.getInt("clicked")

                        when(clicked){
                            1 -> { }
                            2 -> {
                                var showFragment = YApplication.fragmentList.get(YApplication.fragmentList.size - 2)

                                var transaction = supportFragmentManager.beginTransaction()

                                transaction.remove(currentFragment)
                                    .show(showFragment)
                                    .commit()

                                YApplication.removeFragment(currentFragment)

                                var resultData = JSONObject()
                                resultData.put("result", false)
                                resultData.put("type", "restart")
                                listener!!.sendCallback(callback, resultData)
                            }
                            3 -> {
                                exit()
                            }
                        }
                    }
                }
            })

            dialog.create()
            dialog.setBtn1("계속 하기")
            dialog.setBtn2("다시 하기")
            dialog.setBtn3("종료 하기")
            dialog.show()
        } else {
            super.onBackPressed()
        }
    }

    fun exit(){
        finishAndRemoveTask()
        finishAffinity()
        System.exit(0)
        System.runFinalization()
    }
}
