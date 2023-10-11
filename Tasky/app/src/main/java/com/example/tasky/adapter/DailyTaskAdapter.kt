package com.example.tasky.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.HomeFragmentDirections
import com.example.tasky.R
import com.example.tasky.databinding.DailyRowBinding
import com.example.tasky.model.DailyTask

class DailyTaskAdapter(val requireContext: Context, val dailyTaskList: List<DailyTask>) :
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

        holder.binding.root.setOnClickListener{
            val action = HomeFragmentDirections.actionNavigationHomeToDetailDailyTaskFragment(data)
           Navigation.findNavController(it).navigate(action)

        }
    }


}