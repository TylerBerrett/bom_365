package com.tylerb.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tylerb.myapplication.R
import kotlinx.android.synthetic.main.scripture_view.view.*

class ScriptureRecycler(val list: ArrayList<String>):
    RecyclerView.Adapter<ScriptureRecycler.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.scripture_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = list[position]
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val text = view.tv_scripture_view
    }

}