package com.example.tasky

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasky.adapter.DailyTaskAdapter
import com.example.tasky.databinding.FragmentHomeBinding
import com.example.tasky.viewmodel.DailyTaskViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private val viewModel:DailyTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAddNotes.setOnClickListener{
            findNavController().navigate(R.id.addDailyTaskFragment)
        }
        viewModel.getAllDailyTasks.observe(viewLifecycleOwner,{dailyTaskList->

            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.recyclerView.adapter = DailyTaskAdapter(requireContext(), dailyTaskList)

        })
    }

}