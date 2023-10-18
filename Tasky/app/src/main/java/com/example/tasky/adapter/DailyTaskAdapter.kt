package com.example.tasky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.databinding.DailyRowBinding
import com.example.tasky.model.DailyTask

class DailyTaskAdapter(val requireContext: Context, val dailyTaskList: List<DailyTask>, val onItemClick: (view: View, data: DailyTask) -> Unit) :
    RecyclerView.Adapter<DailyTaskAdapter.dailyViewHolder>() {

    inner class dailyViewHolder(val binding: DailyRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dailyViewHolder {
        return dailyViewHolder(
            DailyRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false))
    }

    override fun getItemCount() = dailyTaskList.size

    override fun onBindViewHolder(holder: dailyViewHolder, position: Int) {
        val data = dailyTaskList[position]
        holder.binding.txtNote.text = data.note
        holder.binding.txtTitle.text = data.title
        holder.binding.txtDate.text = data.date
        if(data.ischecked){
            holder.binding.checkbox.setBackgroundResource(com.example.tasky.R.drawable.checkbox_selected)
        } else{
            holder.binding.checkbox.setBackgroundResource(com.example.tasky.R.drawable.checkbox_unselected)
        }

        holder.binding.checkbox.setOnClickListener {
            if(data.ischecked){
                data.ischecked = false
                holder.binding.checkbox.setBackgroundResource(com.example.tasky.R.drawable.checkbox_unselected)
            } else{
                data.ischecked = true
                holder.binding.checkbox.setBackgroundResource(com.example.tasky.R.drawable.checkbox_selected)
            }
        }

        holder.binding.root.setOnClickListener{
//            val action = HomeFragmentDirections.actionNavigationHomeToDetailDailyTaskFragment(data)
//           Navigation.findNavController(it).navigate(action)
            onItemClick(it, data)

        }
    }


}