package com.example.tasky.screen

import android.app.TimePickerDialog
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasky.R
import com.example.tasky.databinding.FragmentUpdateDailyTaskBinding
import com.example.tasky.model.DailyTask
import com.example.tasky.viewmodel.DailyTaskViewModel
import java.util.Calendar
import java.util.Date


class UpdateDailyTaskFragment : Fragment() {

    private var _binding: FragmentUpdateDailyTaskBinding?=null
    private val binding get() = _binding!!

    val notes by navArgs<UpdateDailyTaskFragmentArgs>()
    val viewModel: DailyTaskViewModel by viewModels()

    private var start_hour: Long = 0
    private var start_min: Long = 0
    private var end_hour: Long = 0
    private var end_min: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUpdateDailyTaskBinding.inflate(inflater, container, false)
        val root:View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("@@", "onViewCreated: ${notes.data.title}")
        binding.titleTxt.setText(notes.data.title)
        binding.descriptionTxt.setText(notes.data.note)
        if(notes.data.category== "1"){
            binding.dailyTask.setBackgroundResource(R.drawable.btn_chosen_shape)
            binding.dailyTask.setTextColor(resources.getColor(R.color.white))
            binding.priorityTask.setBackgroundResource(R.drawable.edt_txt_shape)
            binding.priorityTask.setTextColor(resources.getColor(R.color.blue))
        }else{
            binding.priorityTask.setBackgroundResource(R.drawable.btn_chosen_shape)
            binding.priorityTask.setTextColor(resources.getColor(R.color.white))
            binding.dailyTask.setBackgroundResource(R.drawable.edt_txt_shape)
            binding.dailyTask.setTextColor(resources.getColor(R.color.blue))
        }

        binding.end.text = notes.data.timeEnd
        binding.start.text = notes.data.timeStart

        val fm = SimpleDateFormat("HH:mm")
        val timeStart = fm.parse(notes.data.timeStart)
        val timeEnd = fm.parse(notes.data.timeEnd)
        start_hour = timeStart.hours.toLong()
        start_min = timeStart.minutes.toLong()
        end_hour = timeEnd.hours.toLong()
        end_min = timeEnd.minutes.toLong()

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

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


        binding.btnSave.setOnClickListener{
            updateNotes(it)
        }


    }

    private fun updateNotes(it: View?) {
        val title = binding.titleTxt.text.toString()
        val note = binding.descriptionTxt.text.toString()
        val timeStart = binding.start.text.toString()
        val timeEnd = binding.end.text.toString()
        //get current time
        val d = Date()
        val timeCreate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        val dailyTask = DailyTask(
            notes.data.id,
            title = title,
            category = notes.data.category,
            note = note,
            timeStart = timeStart,
            timeEnd = timeEnd,
            ischecked = notes.data.ischecked,
            date = timeCreate.toString()
        )
        viewModel.update(dailyTask)
        Toast.makeText(context, "Notes Updated Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.navigation_home)


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


}