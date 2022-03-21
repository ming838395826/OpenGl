package com.ming.opengl

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ming.opengl.util.JumpUtils

/**
 * @Description 内容适配器
 * @Author ming
 * @Date 2022/3/21 23:37
 */
class ContentAdapter : RecyclerView.Adapter<ContentAdapter.ViewHolder>() {

    private val data: MutableList<Pair<ClickType, String>> = mutableListOf()

    private lateinit var context: Context

    fun setData(input: ArrayList<Pair<ClickType, String>>) {
        data.clear()
        data.addAll(input)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentAdapter.ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_item, null));
    }

    override fun onBindViewHolder(holder: ContentAdapter.ViewHolder, position: Int) {
        holder.tvTitle.text = data[position].second
        holder.itemView.setOnClickListener { JumpUtils.open(context, data[position].first) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTitle: TextView = itemView.findViewById(R.id.tv_name)

    }
}