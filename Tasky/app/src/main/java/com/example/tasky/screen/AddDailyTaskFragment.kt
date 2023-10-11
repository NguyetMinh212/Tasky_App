package com.example.tasky.screen

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tasky.R
import com.example.tasky.databinding.FragmentAddDailyTaskBinding
import com.example.tasky.model.DailyTask
import com.example.tasky.viewmodel.DailyTaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class AddDailyTaskFragment : Fragment() {

    private var _binding: FragmentAddDailyTaskBinding?=null
    private val binding get()= _binding!!

    private var category = "1"
    private val viewModel:DailyTaskViewModel by viewModels()

    private var start_hour: Long = 0
    private var start_min: Long = 0
    private var end_hour: Long = 0
    private var end_min: Long = 0

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

        val cal = android.icu.util.Calendar.getInstance()
        val currentTime = cal.time
        val fm = SimpleDateFormat("HH:mm")
        val formattedTime = fm.format(currentTime)
        binding.start.text = formattedTime
        val h = cal.get(android.icu.util.Calendar.HOUR_OF_DAY)
        val m = cal.get(android.icu.util.Calendar.MINUTE)
        cal.set(android.icu.util.Calendar.MINUTE, m + 10)
        cal.set(android.icu.util.Calendar.HOUR_OF_DAY, h)
        val formattedTime1 = fm.format(cal.time)
        binding.end.text = formattedTime1
        start_hour = h.toLong()
        start_min = m.toLong()
        end_hour = h.toLong()
        end_min = (m + 10).toLong()

        binding.start.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                val c = Calendar.getInstance()
                c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                c.set(Calendar.MINUTE, minute)
                val format = SimpleDateFormat("HH:mm")
                val time: String = format.format(c.time)
                start_hour = hourOfDay.toLong()
                start_min = minute.toLong()
                if(validateTime()) binding.start.text = time
            }, hour, minute, DateFormat.is24HourFormat(context))
            timePickerDialog.show()
        }

        binding.end.setOnClickListener{
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener{ view, hourOfDay, minute ->
                val c = Calendar.getInstance()
                c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                c.set(Calendar.MINUTE, minute)
                val format = SimpleDateFormat("HH:mm")
                val time: String = format.format(c.time)
                end_hour = hourOfDay.toLong()
                end_min = minute.toLong()
                if(validateTime()) binding.end.text = time
            }, hour, minute, DateFormat.is24HourFormat(context))
            timePickerDialog.show()
        }

        binding.dailyTask.setOnClickListener {
            category = "1"
            binding.dailyTask.setBackgroundResource(R.drawable.btn_chosen_shape)
            binding.dailyTask.setTextColor(resources.getColor(R.color.white))
            binding.priorityTask.setBackgroundResource(R.drawable.edt_txt_shape)
            binding.priorityTask.setTextColor(resources.getColor(R.color.blue))
        }
        binding.priorityTask.setOnClickListener {
            category = "2"
            binding.priorityTask.setBackgroundResource(R.drawable.btn_chosen_shape)
            binding.priorityTask.setTextColor(resources.getColor(R.color.white))
            binding.dailyTask.setBackgroundResource(R.drawable.edt_txt_shape)
            binding.dailyTask.setTextColor(resources.getColor(R.color.blue))
        }
    }

    private fun validateTime(): Boolean {
        val start = binding.start.text.toString()
        val end = binding.end.text.toString()
        if (start.isEmpty() || end.isEmpty()) {
            Toast.makeText(context, "Please fill the time", Toast.LENGTH_SHORT).show()
            return false
        }

        else if (start_hour > end_hour || (start_hour == end_hour && start_min > end_min - 5)) {
            Toast.makeText(context, "Time for task is at least 5 minutes", Toast.LENGTH_SHORT)
                .show()
            end_hour = start_hour
            end_min = start_min + 5
            return false
        }
        return true
    }

    private fun createDailyTask(it: View?) {
        val title = binding.titleTxt.text.toString()
        val note = binding.descriptionTxt.text.toString()
        val timeStart = binding.timeStart.text.toString()
        val timeEnd = binding.timeEnd.text.toString()

        //get current time
        val d = Date()
        val timeCreate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        val dailyTask = DailyTask(
            null,
            title = title,
            category = category,
            note = note,
            timeStart = timeStart,
            timeEnd = timeEnd,
            date = timeCreate.toString()
        )
        viewModel.insert(dailyTask)
        Toast.makeText(context, "Notes Created Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.navigation_home)
    }


}