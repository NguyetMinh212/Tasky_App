package com.example.tasky.screen

import android.app.DatePickerDialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.R
import com.example.tasky.adapter.SubTaskAdapter
import com.example.tasky.databinding.FragmentAddDailyTaskBinding
import com.example.tasky.model.DailyTask
import com.example.tasky.model.PriorityTask
import com.example.tasky.model.SubTask
import com.example.tasky.viewmodel.DailyTaskViewModel
import com.example.tasky.viewmodel.PriorityTaskViewModel
import com.example.tasky.viewmodel.SubTaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


class AddDailyTaskFragment : Fragment() {

    private var _binding: FragmentAddDailyTaskBinding? = null
    private val binding get() = _binding!!

    private var category = "1"
    private val viewModel: DailyTaskViewModel by viewModels()
    private val viewModelPriority: PriorityTaskViewModel by viewModels()
    private val viewModelSubTask: SubTaskViewModel by viewModels()
    private lateinit var adapter : SubTaskAdapter
    private lateinit var subTaskRV : RecyclerView

    private var start_hour: Long = 0
    private var start_min: Long = 0
    private var end_hour: Long = 0
    private var end_min: Long = 0

    private lateinit var startDate: Calendar
    private lateinit var endDate: Calendar

    private val subTaskList = mutableListOf<SubTask>()


    private var idTask = 0

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
        binding.saveBtn.setOnClickListener {
            if (category == "1") {

                    createDailyTask(it)

            } else if (category == "2") {
                if (validateDate()) {
                    createPriorityTask(it)
                }
            }
        }

        category = "1"
        binding.dailyTask.setBackgroundResource(R.drawable.btn_chosen_shape)
        binding.dailyTask.setTextColor(resources.getColor(R.color.white))
        binding.priorityTask.setBackgroundResource(R.drawable.edt_txt_shape)
        binding.priorityTask.setTextColor(resources.getColor(R.color.blue))

        initForDailyTask()

        binding.dailyTask.setOnClickListener {
            category = "1"
            binding.dailyTask.setBackgroundResource(R.drawable.btn_chosen_shape)
            binding.dailyTask.setTextColor(resources.getColor(R.color.white))
            binding.priorityTask.setBackgroundResource(R.drawable.edt_txt_shape)
            binding.priorityTask.setTextColor(resources.getColor(R.color.blue))
            initForDailyTask()
        }
        binding.priorityTask.setOnClickListener {
            category = "2"
            adapter.notifyDataSetChanged()
            binding.priorityTask.setBackgroundResource(R.drawable.btn_chosen_shape)
            binding.priorityTask.setTextColor(resources.getColor(R.color.white))
            binding.dailyTask.setBackgroundResource(R.drawable.edt_txt_shape)
            binding.dailyTask.setTextColor(resources.getColor(R.color.blue))
            initForPriorityTask()
        }
        setUpAdapter()

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setUpAdapter() {
        subTaskRV = binding.recyclerView
        adapter = SubTaskAdapter(requireContext(), subTaskList,{ it, data ->
            val action =
                AddDailyTaskFragmentDirections.actionAddDailyTaskFragmentToUpdateSubTaskFragment(data)
            findNavController().navigate(action)}){ it, data -> viewModelSubTask.update(data)}
        subTaskRV.layoutManager = LinearLayoutManager(requireContext())
        subTaskRV.adapter = adapter
    }


    private fun validateTime(): Boolean {
        val start = binding.start.text.toString()
        val end = binding.end.text.toString()
        if (start.isEmpty() || end.isEmpty()) {
            Toast.makeText(context, "Please fill the time", Toast.LENGTH_SHORT).show()
            return false
        } else if (start_hour > end_hour || (start_hour == end_hour && start_min > end_min - 5)) {
            Toast.makeText(context, "Time for task is at least 5 minutes", Toast.LENGTH_SHORT)
                .show()
            end_hour = start_hour
            end_min = start_min + 5
            return false
        }
        return true
    }

    private fun validateDate(): Boolean {
        val start = binding.start.text.toString()
        val end = binding.end.text.toString()
        if (start.isEmpty() || end.isEmpty()) {
            Toast.makeText(context, "Please fill the date", Toast.LENGTH_SHORT).show()
            return false
        } else if (endDate.before(startDate)) {
            Toast.makeText(context, "End date must be after start date", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }

    private fun createDailyTask(it: View?) {
        val title = binding.titleTxt.text.toString()
        val note = binding.descriptionTxt.text.toString()
        val timeStart = binding.start.text.toString()
        val timeEnd = binding.end.text.toString()

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
            ischecked = false,
            date = timeCreate.toString()
        )
        viewModel.insert(dailyTask)
        Toast.makeText(context, "Notes Created Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.navigation_home)
    }

    private fun createPriorityTask(it: View?) {
        val title = binding.titleTxt.text.toString()
        val note = binding.descriptionTxt.text.toString()
        val timeStart = binding.start.text.toString()
        val timeEnd = binding.end.text.toString()

        //get current time
        val d = Date()
        val timeCreate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        val priorityTask = PriorityTask(
            title = title,
            category = category,
            description = note,
            timeStart = timeStart,
            timeEnd = timeEnd,
            dayCreated = timeCreate.toString()
        )
        viewModelPriority.savePriorityTask(priorityTask, subTaskList)
        Toast.makeText(context, "Priority Task Created Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.navigation_home)
    }

    private fun initForDailyTask() {

        binding.todoList.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.addSubtask.visibility = View.GONE
        binding.subtitletxt.visibility = View.GONE

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
            val timePickerDialog = TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val c = Calendar.getInstance()
                    c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    c.set(Calendar.MINUTE, minute)
                    val format = SimpleDateFormat("HH:mm")
                    val time: String = format.format(c.time)
                    start_hour = hourOfDay.toLong()
                    start_min = minute.toLong()
                    if (validateTime()) binding.start.text = time
                },
                hour,
                minute,
                DateFormat.is24HourFormat(context)
            )
            timePickerDialog.show()
        }

        binding.end.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    val c = Calendar.getInstance()
                    c.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    c.set(Calendar.MINUTE, minute)
                    val format = SimpleDateFormat("HH:mm")
                    val time: String = format.format(c.time)
                    end_hour = hourOfDay.toLong()
                    end_min = minute.toLong()
                    if (validateTime()) binding.end.text = time
                },
                hour,
                minute,
                DateFormat.is24HourFormat(context)
            )
            timePickerDialog.show()
        }

    }

    private fun initForPriorityTask() {

        binding.todoList.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.VISIBLE
        binding.addSubtask.visibility = View.VISIBLE
        binding.subtitletxt.visibility = View.VISIBLE

        startDate = Calendar.getInstance()
        endDate = Calendar.getInstance()
        // Set the start date as the current date
        startDate.timeInMillis = System.currentTimeMillis()

        // Set the end date as one day after the start date
        endDate.timeInMillis = startDate.timeInMillis
        endDate.add(Calendar.DAY_OF_MONTH, 1)

        val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        val formattedStartDate = dateFormat.format(startDate.time)
        binding.start.text = formattedStartDate
        val formattedEndDate = dateFormat.format(endDate.time)
        binding.end.text = formattedEndDate

        binding.start.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    startDate.set(Calendar.YEAR, year)
                    startDate.set(Calendar.MONTH, month)
                    startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(startDate.time)
                    if (validateDate()) binding.start.text = formattedDate
                },
                startDate.get(Calendar.YEAR),
                startDate.get(Calendar.MONTH),
                startDate.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        binding.end.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    endDate.set(Calendar.YEAR, year)
                    endDate.set(Calendar.MONTH, month)
                    endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(endDate.time)
                    if (validateDate()) binding.end.text = formattedDate
                },
                endDate.get(Calendar.YEAR),
                endDate.get(Calendar.MONTH),
                endDate.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        binding.addSubtask.setOnClickListener {
            val subtitle = binding.subtitletxt.text.toString()
            if (subtitle.isNotEmpty()) {
                val subTask = SubTask(
                    title = subtitle,
                    idTask = idTask,
                    isDone = false
                )
                subTaskList.add(subTask)
                Toast.makeText(context, "Subtask added successfully!", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                binding.subtitletxt.text.clear()
            } else {
                Toast.makeText(context, "Please fill the subtitle", Toast.LENGTH_SHORT).show()
            }

        }
    }


}