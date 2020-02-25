package com.yam.liar.view.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.yam.core.util.Config
import kotlinx.android.synthetic.main.activity_settings.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import com.yam.liar.R


class YNetworkActivity : Activity(), View.OnClickListener {
    lateinit var btnSave: Button
    lateinit var btnClose: ImageButton
    lateinit var spMode: Spinner
    lateinit var editUrl: EditText
    lateinit var editDirectoryPath: EditText

    lateinit var sharedPreferences : SharedPreferences

    val permissionRequestCode = 10001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.yam.liar.R.layout.activity_settings)

        btnSave = btn_save
        btnClose = btn_close
        spMode = spinner_mode
        editUrl = edit_url
        editDirectoryPath = edit_directory_path

        var spinnerAdapter = ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.settings_modes)
        )
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spMode.adapter = spinnerAdapter

        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE)

        var contentsMode = sharedPreferences.getString("contents_mode", Config.MODE_ASSETS)!!
        var contentsUrl = sharedPreferences.getString("contents_url", "")!!
        var contentsExternalDirectoryPath = sharedPreferences.getString("contents_external_directory_path", "")!!

        editUrl.setText(contentsUrl)
        editDirectoryPath.setText(contentsExternalDirectoryPath)

        if (contentsMode.equals(Config.MODE_ASSETS)) {
            spMode.setSelection(0)
            ll_url.visibility = View.GONE
            ll_directory_path.visibility = View.GONE
        } else if (contentsMode.equals(Config.MODE_ABSOLUTE)) {
            spMode.setSelection(1)
            ll_url.visibility = View.VISIBLE
            ll_directory_path.visibility = View.GONE
        } else {
            spMode.setSelection(2)
            ll_url.visibility = View.GONE
            ll_directory_path.visibility = View.VISIBLE
        }

        btnSave.setOnClickListener(this)
        btnClose.setOnClickListener(this)

        spMode.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View,
                position: Int,
                id: Long
            ) {
                var value = spMode.getItemAtPosition(position)

                if(value.equals(Config.MODE_ASSETS)){
                    ll_url.visibility = View.GONE
                    ll_directory_path.visibility = View.GONE
                } else if(value.equals(Config.MODE_ABSOLUTE)) {
                    ll_url.visibility = View.VISIBLE
                    ll_directory_path.visibility = View.GONE
                } else {
                    ll_url.visibility = View.GONE
                    ll_directory_path.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
            }

        })
    }

    override fun onClick(view: View?) {
        when (view) {
            btnClose -> {
                finish()
            }
            btnSave -> {
                if(spMode.selectedItem.equals(Config.MODE_EXTERNAL)){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), permissionRequestCode)
                            return
                        }
                    }
                }

                var editor = sharedPreferences.edit()

                editor.putString("contents_mode", spMode.selectedItem.toString())
                editor.putString("contents_url", editUrl.text.toString())
                editor.putString("contents_external_directory_path", editDirectoryPath.text.toString())

                editor.commit()

                finish()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var result = true

        for(grantResult in grantResults){
            if(grantResult == PackageManager.PERMISSION_DENIED){
                result = false
            }
        }

        if(result){
            var editor = sharedPreferences.edit()

            editor.putString("contents_mode", spMode.selectedItem.toString())
            editor.putString("contents_url", editUrl.text.toString())
            editor.putString("contents_external_directory_path", editDirectoryPath.text.toString())

            editor.commit()

            finish()
        } else {
            Toast.makeText(this, resources.getString(R.string.settings_save_fail), Toast.LENGTH_SHORT).show()
        }
    }
}