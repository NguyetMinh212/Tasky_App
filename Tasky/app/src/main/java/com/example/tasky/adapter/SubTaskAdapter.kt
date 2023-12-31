package com.example.tasky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.databinding.SubtaskBinding
import com.example.tasky.model.SubTask

class SubTaskAdapter(
    val requireContext: Context,
    val subTaskList: List<SubTask>,
    val onItemClick: (view: View, data: SubTask) -> Unit,
    val onItemClick2: (view: View, data: SubTask) -> Unit,
) : RecyclerView.Adapter<SubTaskAdapter.SubTaskViewHolder>() {

    inner class SubTaskViewHolder(val binding: SubtaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubTaskViewHolder {
        return SubTaskViewHolder(
            SubtaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = subTaskList.size

    override fun onBindViewHolder(holder: SubTaskViewHolder, position: Int) {
        val data = subTaskList[position]
        holder.binding.subtitle.text = data.title

        if (data.isDone) {
            holder.binding.checkbox.setBackgroundResource(com.example.tasky.R.drawable.active)
        } else {
            holder.binding.checkbox.setBackgroundResource(com.example.tasky.R.drawable.inactive)
        }

        holder.binding.checkbox.setOnClickListener {
            if (data.isDone) {
                data.isDone = false
                subTaskList[position].isDone = false
                holder.binding.checkbox.setBackgroundResource(com.example.tasky.R.drawable.inactive)
            } else {
                data.isDone = true
                subTaskList[position].isDone = true
                holder.binding.checkbox.setBackgroundResource(com.example.tasky.R.drawable.active)
            }
            onItemClick2(it, data)
        }
        holder.binding.root.setOnClickListener {
            onItemClick(it, data)
        }
    }


}