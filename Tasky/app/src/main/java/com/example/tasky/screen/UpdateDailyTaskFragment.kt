package com.example.tasky.screen

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasky.R
import com.example.tasky.databinding.FragmentUpdateDailyTaskBinding
import com.example.tasky.model.DailyTask
import com.example.tasky.viewmodel.DailyTaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Date


class UpdateDailyTaskFragment : Fragment() {

    private var _binding: FragmentUpdateDailyTaskBinding?=null
    private val binding get() = _binding!!

    val notes by navArgs<UpdateDailyTaskFragmentArgs>()
    val viewModel: DailyTaskViewModel by viewModels()
    private var category = "1"
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
        binding.edittitleTxt.setText(notes.data.title)
        binding.editsubtitleTxt.setText(notes.data.subtitle)
        binding.editnoteTxt.setText(notes.data.note)

        binding.btnEditsave.setOnClickListener{
            updateNotes(it)
        }

        binding.btnDelete.setOnClickListener {
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialog_delete)
            bottomSheet.show()

            val textViewYes = bottomSheet.findViewById<TextView>(R.id.dialog_yes)
            val textViewNo = bottomSheet.findViewById<TextView>(R.id.dialog_no)

            textViewYes?.setOnClickListener{
                viewModel.delete(notes.data.id!!)
                bottomSheet.dismiss()
                findNavController().navigate(R.id.navigation_home)
            }

            textViewNo?.setOnClickListener{
                bottomSheet.dismiss()
            }
        }
    }

    private fun updateNotes(it: View?) {
        val title = binding.edittitleTxt.text.toString()
        val subtitle = binding.editsubtitleTxt.text.toString()
        val note = binding.editnoteTxt.text.toString()

        //get current time
        val d = Date()
        val timeCreate: CharSequence = DateFormat.format("MMMM d, yyyy ", d.time)

        val dailyTask = DailyTask(
            notes.data.id,
            title = title,
            subtitle = subtitle,
            category = category,
            note = note,
            date = timeCreate.toString()
        )
        viewModel.update(dailyTask)
        Toast.makeText(context, "Notes Updated Successfully", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.navigation_home)


    }


}