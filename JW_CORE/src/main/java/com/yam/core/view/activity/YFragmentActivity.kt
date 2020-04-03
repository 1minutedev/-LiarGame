package com.yam.core.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.*
import android.view.accessibility.AccessibilityEvent
import androidx.fragment.app.FragmentActivity
import com.yam.core.application.YApplication
import com.yam.core.view.fragment.YFragment
import com.yam.core.view.fragment.YWebFragment

open class YFragmentActivity : FragmentActivity() {

    companion object {
        private val TAG = YFragmentActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d(TAG, "onCreate");
    }

    override fun onStart() {
        super.onStart()

        val cb = window.callback

        window.callback = (object : Window.Callback {

            override fun onSearchRequested(searchEvent: SearchEvent): Boolean {
                return false
            }

            override fun onWindowStartingActionMode(
                callback: ActionMode.Callback,
                type: Int
            ): ActionMode? {
                return null
            }

            override fun onWindowFocusChanged(hasFocus: Boolean) {
                cb.onWindowFocusChanged(hasFocus)
            }

            override fun onWindowAttributesChanged(attrs: WindowManager.LayoutParams) {
                cb.onWindowAttributesChanged(attrs)
            }

            override fun onSearchRequested(): Boolean {
                return cb.onSearchRequested()
            }

            override fun onPreparePanel(featureId: Int, view: View?, menu: Menu): Boolean {
                return cb.onPreparePanel(featureId, view, menu)
            }

            override fun onPanelClosed(featureId: Int, menu: Menu) {
                cb.onPanelClosed(featureId, menu)
            }

            override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
                return cb.onMenuOpened(featureId, menu)
            }

            override fun onMenuItemSelected(featureId: Int, item: MenuItem): Boolean {
                return cb.onMenuItemSelected(featureId, item)
            }

            override fun onDetachedFromWindow() {
                cb.onDetachedFromWindow()
            }

            override fun onCreatePanelView(featureId: Int): View? {
                return cb.onCreatePanelView(featureId)
            }

            override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
                return cb.onCreatePanelMenu(featureId, menu)
            }

            override fun onContentChanged() {
                cb.onContentChanged()
            }

            override fun onAttachedToWindow() {
                cb.onAttachedToWindow()
            }

            override fun dispatchTrackballEvent(event: MotionEvent): Boolean {
                return cb.dispatchTrackballEvent(event)
            }

            override fun dispatchTouchEvent(event: MotionEvent): Boolean {
                return dispatchTouchEvent(cb, event)
            }

            override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent): Boolean {
                return cb.dispatchPopulateAccessibilityEvent(event)
            }

            override fun dispatchKeyEvent(event: KeyEvent): Boolean {
                return cb.dispatchKeyEvent(event)
            }

            override fun dispatchGenericMotionEvent(arg0: MotionEvent): Boolean {
                return cb.dispatchGenericMotionEvent(arg0)
            }

            override fun dispatchKeyShortcutEvent(event: KeyEvent): Boolean {
                return cb.dispatchKeyShortcutEvent(event)
            }

            override fun onActionModeFinished(mode: ActionMode) {

            }

            override fun onActionModeStarted(mode: ActionMode) {

            }

            override fun onWindowStartingActionMode(callback: ActionMode.Callback): ActionMode? {
                return null
            }
        })
    }

    fun dispatchTouchEvent(cb: Window.Callback, event: MotionEvent): Boolean {
        var fragment = YApplication.fragmentList.get(YApplication.fragmentList.size - 1)
        return (fragment as YFragment).dispatchTouchEvent(cb, event)
    }

    override fun onBackPressed() {
        if(YApplication.fragmentList.size > 1) {
            var currentFragment = YApplication.fragmentList.get(YApplication.fragmentList.size - 1)

            if(currentFragment is YWebFragment) {
                (currentFragment as YWebFragment).backButtonEvent()
            } else {
                var showFragment = YApplication.fragmentList.get(YApplication.fragmentList.size - 2)

                var transaction = supportFragmentManager.beginTransaction()

                transaction.remove(currentFragment)
                    .show(showFragment)
                    .commit()

                YApplication.removeFragment(currentFragment)
            }
        } else {
            super.onBackPressed()
        }
    }
}