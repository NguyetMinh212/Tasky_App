package com.example.tasky.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasky.R
import com.example.tasky.databinding.FragmentUpdateSubTaskBinding
import com.example.tasky.model.SubTask
import com.example.tasky.viewmodel.SubTaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog

class UpdateSubTaskFragment : Fragment() {

    private var _binding: FragmentUpdateSubTaskBinding? = null
    private val binding get() = _binding!!
    val subTask by navArgs<UpdateSubTaskFragmentArgs>()
    val subTaskViewModel: SubTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUpdateSubTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleTxt.setText(subTask.data.title)
        binding.editBtn.setOnClickListener {
            updateSubTask(it)
        }

        binding.deleteBtn.setOnClickListener {
            val bottomSheet: BottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)
            bottomSheet.show()

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textViewYes?.setOnClickListener {
                subTaskViewModel.delete(subTask.data.idSubTask)
                bottomSheet.dismiss()
                findNavController().popBackStack()
            }

            textViewNo?.setOnClickListener {
                bottomSheet.dismiss()
            }
        }
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun updateSubTask(it: View?) {
        val title = binding.titleTxt.text.toString()
        val idSubTask = subTask.data.idSubTask
        val idTask = subTask.data.idTask
        val isDone = subTask.data.isDone

        if (title.isNotEmpty()) {
            val subTask = SubTask(idSubTask, title, isDone, idTask)
            subTaskViewModel.update(subTask)
            Toast.makeText(context, "Subtask Updated Successfully", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

}