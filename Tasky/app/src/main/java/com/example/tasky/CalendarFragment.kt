package com.example.tasky

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasky.adapter.DailyTaskAdapter
import com.example.tasky.databinding.FragmentCalendarBinding
import com.example.tasky.viewmodel.DailyTaskViewModel
import java.util.Calendar
import java.util.Date
import java.util.Locale


class CalendarFragment : Fragment() {

    private var _binding : FragmentCalendarBinding?=null
    private val binding get() = _binding!!

    private lateinit var datePicker: DatePicker
    private val viewModel: DailyTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root
        datePicker = binding.datePicker
        val d = Date()
        val timeCreate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)
        binding.dateTv.text = timeCreate.toString()
        showRecyclerView(binding.dateTv.text.toString())
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, monthOfYear, dayOfMonth)
                }
                val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.dateTv.text = formattedDate
                showRecyclerView(formattedDate)
            }
        }

    }

    private fun showRecyclerView(searchQuery:String){
        viewModel.calendarSearch("%$searchQuery%").observe(viewLifecycleOwner,{dailyTaskList->
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = DailyTaskAdapter(requireContext(), dailyTaskList){
                    it, data ->
                val action = CalendarFragmentDirections.actionNavigationCalendarToDetailDailyTaskFragment(data)
                Navigation.findNavController(it).navigate(action)
            }
        })
    }

}