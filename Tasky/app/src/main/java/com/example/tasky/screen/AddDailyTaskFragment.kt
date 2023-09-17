package com.example.tasky.screen

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tasky.R
import com.example.tasky.databinding.FragmentAddDailyTaskBinding
import com.example.tasky.model.DailyTask
import com.example.tasky.viewmodel.DailyTaskViewModel
import java.util.Date


class AddDailyTaskFragment : Fragment() {

    private var _binding: FragmentAddDailyTaskBinding?=null
    private val binding get()= _binding!!

    private var category = "1"
    private val viewModel:DailyTaskViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddDailyTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener{
            createDailyTask(it)
        }
        binding.dailyTask.setOnClickListener{
            category = "1"
        }
        binding.priorityTask.setOnClickListener {
            category = "2"
        }
    }

    private fun createDailyTask(it: View?) {
        val title = binding.titleTxt.text.toString()
        val subtitle = binding.subtitleTxt.text.toString()
        val note = binding.noteTxt.text.toString()

        //get current time
        val d = Date()
        val timeCreate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        val dailyTask = DailyTask(
            null,
            title = title,
            subtitle = subtitle,
            category = category,
            note = note,
            date = timeCreate.toString()
        )
        viewModel.insert(dailyTask)
        Toast.makeText(context, "Notes Created Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.navigation_home)
    }
}