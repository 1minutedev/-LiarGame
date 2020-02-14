package com.yam.core.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.fragment.app.FragmentActivity

open class YFragmentActivity : FragmentActivity() {

    companion object {
        private val TAG = YFragmentActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.d(TAG, "onCreate");
    }
}