package com.example.tasky.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.adapter.SubTaskAdapter
import com.example.tasky.databinding.FragmentUpdatePriorityTaskBinding
import com.example.tasky.viewmodel.PriorityTaskViewModel
import com.example.tasky.viewmodel.SubTaskViewModel
import java.util.Calendar


class UpdatePriorityTaskFragment : Fragment() {

    private var _binding: FragmentUpdatePriorityTaskBinding?=null
    private val binding get() = _binding!!

    private val viewModelPriority: PriorityTaskViewModel by viewModels()
    private val viewModelSubTask: SubTaskViewModel by viewModels()
    private lateinit var adapter : SubTaskAdapter
    private lateinit var subTaskRV : RecyclerView

    private lateinit var startDate: Calendar
    private lateinit var endDate: Calendar

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

    }
}