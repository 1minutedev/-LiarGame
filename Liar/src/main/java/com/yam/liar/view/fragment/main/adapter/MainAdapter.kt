package com.yam.liar.view.fragment.native.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yam.core.util.RUtil
import com.yam.liar.view.fragment.native.item.PostItem
import kotlinx.android.synthetic.main.item_post.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    var items: MutableList<PostItem> = mutableListOf(
        PostItem("Title1", "Content1"),
        PostItem("Title2", "Content2"),
        PostItem("Title3", "Content3")
    )

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MainViewHolder(parent)


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holer: MainViewHolder, position: Int) {
        items[position].let { item ->
            with(holer) {
                tvTitle.text = item.title
                tvContent.text = item.content
            }
        }
    }

    inner class MainViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(RUtil.getLayoutR(parent.context, "item_post"), parent, false)
    ) {
        val tvTitle = itemView.tv_main_title
        val tvContent = itemView.tv_main_content
    }
}

