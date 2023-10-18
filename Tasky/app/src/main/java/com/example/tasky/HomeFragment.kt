package com.example.tasky

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tasky.adapter.DailyTaskAdapter
import com.example.tasky.adapter.PriorityTaskAdapter
import com.example.tasky.databinding.FragmentHomeBinding
import com.example.tasky.viewmodel.DailyTaskViewModel
import com.example.tasky.viewmodel.PriorityTaskViewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DailyTaskViewModel by viewModels()

    private val priorityViewModel: PriorityTaskViewModel by viewModels()

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
        binding.btnAddNotes.setOnClickListener {
            findNavController().navigate(R.id.addDailyTaskFragment)
        }
        viewModel.getAllDailyTasks.observe(viewLifecycleOwner, { dailyTaskList ->

            binding.recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            binding.recyclerView.adapter =
                DailyTaskAdapter(requireContext(), dailyTaskList) { it, data ->
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToDetailDailyTaskFragment(data)
                    Navigation.findNavController(it).navigate(action)
                }


        })

        priorityViewModel.getAllPriorityTasks.observe(viewLifecycleOwner, { priorityTaskList ->
            binding.recyclerViewHorizontal.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerViewHorizontal.adapter =
                PriorityTaskAdapter(requireContext(), priorityTaskList) { it, data ->
                    val action =
                        HomeFragmentDirections.actionNavigationHomeToDetailPriorityTaskFragment(data)
                    Navigation.findNavController(it).navigate(action)
                }
        })
        binding.recyclerView.adapter?.notifyDataSetChanged()
        binding.recyclerViewHorizontal.adapter?.notifyDataSetChanged()
    }

}