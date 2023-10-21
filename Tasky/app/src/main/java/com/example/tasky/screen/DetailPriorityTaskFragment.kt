package com.example.tasky.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.R
import com.example.tasky.adapter.SubTaskAdapter
import com.example.tasky.databinding.FragmentDetailPriorityTaskBinding
import com.example.tasky.viewmodel.PriorityTaskViewModel
import com.example.tasky.viewmodel.SubTaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class DetailPriorityTaskFragment : Fragment() {

    private var _binding: FragmentDetailPriorityTaskBinding? = null
    private val binding get() = _binding!!
    val priorityTask by navArgs<DetailPriorityTaskFragmentArgs>()
    private val viewModelPriority: PriorityTaskViewModel by viewModels()
    private val viewModelSubTask: SubTaskViewModel by viewModels()
    private lateinit var subTaskRV : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailPriorityTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleTv.text = priorityTask.data.title
        binding.note.text = priorityTask.data.description
        binding.date.text = priorityTask.data.dayCreated
        binding.start.text = priorityTask.data.timeStart
        binding.end.text = priorityTask.data.timeEnd

        setUpAdapter()

        binding.editBtn.setOnClickListener {
            val action = DetailPriorityTaskFragmentDirections.actionDetailPriorityTaskFragmentToUpdatePriorityTaskFragment(priorityTask.data)
            Navigation.findNavController(it).navigate(action)
        }
        binding.deleteBtn.setOnClickListener {
            val bottomSheet: BottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)
            bottomSheet.show()

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textViewYes?.setOnClickListener {
                viewModelPriority.delete(priorityTask.data.idTask)
                bottomSheet.dismiss()
                findNavController().navigate(R.id.navigation_home)
            }

            textViewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }
        }
    }

    private fun setUpAdapter() {
        subTaskRV = binding.recyclerView
        viewModelSubTask.getAllSubTaskFromPriorityTask(priorityTask.data.idTask).observe(viewLifecycleOwner, { subTaskList ->
            subTaskRV.adapter = SubTaskAdapter(requireContext(), subTaskList) { it, data ->
                val action =
                    DetailPriorityTaskFragmentDirections.actionDetailPriorityTaskFragmentToUpdateSubTaskFragment(data)
                    Navigation.findNavController(it).navigate(action)
            }
        })
        subTaskRV.adapter?.notifyDataSetChanged()
        subTaskRV.layoutManager = LinearLayoutManager(requireContext())

    }
}