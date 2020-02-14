package com.yam.liar.view.fragment.main

import android.R
import android.app.Activity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.yam.core.view.fragment.YFragment
import com.yam.liar.view.fragment.native.adapter.MainAdapter
import com.yam.liar.view.fragment.web.WebFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainPresenter : MainContract.Presenter {
    private var view: MainContract.View? = null
    private var fragment: MainFragment? = null
    private var activity: Activity? = null

    var foodCategories = arrayOf("전체", "한식", "일식", "중식", "양식")

    override fun setView(view: MainContract.View) {
        this.view = view
        this.fragment = (view as MainFragment)
        this.activity = fragment!!.activity
    }

    override fun showLoginPage() {
        var fragment = WebFragment()
        var arguments = Bundle()

        fragment.arguments = arguments

        (view as YFragment).moveToFragment(fragment, "top")
    }

    override fun initLayout() {
        val foodCategoryAdapter = ArrayAdapter(this.activity!!.applicationContext, R.layout.simple_spinner_dropdown_item, foodCategories)
        this.fragment!!.spinner_category_food.adapter = foodCategoryAdapter

        this.fragment!!.rv_main_list.adapter = MainAdapter()
        this.fragment!!.rv_main_list.layoutManager = LinearLayoutManager(this.activity)
    }
}