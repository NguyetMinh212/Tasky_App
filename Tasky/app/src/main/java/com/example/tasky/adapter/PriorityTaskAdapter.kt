package com.example.tasky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.databinding.PrioritytaskItemBinding
import com.example.tasky.model.PriorityTask

class PriorityTaskAdapter(
    val requireContext: Context,
    val priorityTaskList: List<PriorityTask>,
    val onItemClick: (view: View, data: PriorityTask) -> Unit,
) : RecyclerView.Adapter<PriorityTaskAdapter.PriorityTaskViewHolder>() {

    inner class PriorityTaskViewHolder(val binding: PrioritytaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriorityTaskViewHolder {
        return PriorityTaskViewHolder(
            PrioritytaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = priorityTaskList.size

    override fun onBindViewHolder(holder: PriorityTaskViewHolder, position: Int) {
        val data = priorityTaskList[position]
        holder.binding.titleTxt.text = data.title
        holder.binding.dateTxt.text = data.dayCreated
        holder.binding.root.setOnClickListener {
            onItemClick(it, data)
        }
    }
}
