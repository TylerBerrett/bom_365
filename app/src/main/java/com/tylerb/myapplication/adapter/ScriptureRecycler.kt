package com.tylerb.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tylerb.myapplication.R
import com.tylerb.myapplication.databinding.ScriptureViewBinding

class ScriptureRecycler(val list: ArrayList<String>):
    RecyclerView.Adapter<ScriptureRecycler.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ScriptureViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = list[position]
    }

    class ViewHolder(binding: ScriptureViewBinding): RecyclerView.ViewHolder(binding.root){
        val text = binding.tvScriptureView
    }

}