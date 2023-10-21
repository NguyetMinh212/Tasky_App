package com.example.tasky.screen

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.R
import com.example.tasky.adapter.SubTaskAdapter
import com.example.tasky.databinding.FragmentUpdatePriorityTaskBinding
import com.example.tasky.model.SubTask
import com.example.tasky.viewmodel.PriorityTaskViewModel
import com.example.tasky.viewmodel.SubTaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class UpdatePriorityTaskFragment : Fragment() {

    private var _binding: FragmentUpdatePriorityTaskBinding?=null
    private val binding get() = _binding!!

    private val viewModelPriority: PriorityTaskViewModel by viewModels()
    private val viewModelSubTask: SubTaskViewModel by viewModels()

    private lateinit var subTaskRV : RecyclerView
    private val subTaskList = mutableListOf<SubTask>()

    private lateinit var startDate: Calendar
    private lateinit var endDate: Calendar

    val priorityTask by navArgs<UpdatePriorityTaskFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdatePriorityTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleTxt.setText(priorityTask.data.title)
        binding.descriptionTxt.setText(priorityTask.data.description)
        if(priorityTask.data.category== "1"){
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

        binding.end.text = priorityTask.data.timeEnd
        binding.start.text = priorityTask.data.timeStart

        setUpAdapter()

        binding.todoList.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.VISIBLE
        binding.addSubtask.visibility = View.VISIBLE

        startDate = Calendar.getInstance()
        endDate = Calendar.getInstance()

        val fm =  SimpleDateFormat("MMMM d, yyyy")
        val dateStart = fm.parse(priorityTask.data.timeStart)
        val dateEnd = fm.parse(priorityTask.data.timeEnd)
        startDate.time = dateStart
        endDate.time = dateEnd

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
                    idTask = priorityTask.data.idTask,
                    isDone = false
                )
                viewModelSubTask.insert(subTask)
                Toast.makeText(context, "Subtask added successfully!", Toast.LENGTH_SHORT).show()
                subTaskRV.adapter?.notifyDataSetChanged()
                binding.subtitletxt.text.clear()
            } else {
                Toast.makeText(context, "Please fill the subtitle", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnSave.setOnClickListener {
            updatePriorityTask(it)
        }

    }

    private fun updatePriorityTask(it: View?) {
        val title = binding.titleTxt.text.toString()
        val description = binding.descriptionTxt.text.toString()
        val timeStart = binding.start.text.toString()
        val timeEnd = binding.end.text.toString()
        val category = priorityTask.data.category
        //get current time
        val d = java.util.Date()
        val timeCreate: CharSequence = SimpleDateFormat("MMMM d, yyyy ", Locale.getDefault()).format(d.time)

        val priorityTask = com.example.tasky.model.PriorityTask(
            idTask = priorityTask.data.idTask,
            title = title,
            description = description,
            timeStart = timeStart,
            timeEnd = timeEnd,
            category = category,
            dayCreated = timeCreate.toString()
        )
        viewModelPriority.update(priorityTask)
        Toast.makeText(context, "Priority Task Updated Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.navigation_home)
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

    private fun setUpAdapter() {
        subTaskRV = binding.recyclerView
        viewModelSubTask.getAllSubTaskFromPriorityTask(priorityTask.data.idTask).observe(viewLifecycleOwner, { subTaskList ->
            subTaskRV.adapter = SubTaskAdapter(requireContext(), subTaskList) { it, data ->
                val action =
                    UpdatePriorityTaskFragmentDirections.actionUpdatePriorityTaskFragmentToUpdateSubTaskFragment(data)
                Navigation.findNavController(it).navigate(action)
            }
        })
        subTaskRV.layoutManager = LinearLayoutManager(requireContext())

    }
}