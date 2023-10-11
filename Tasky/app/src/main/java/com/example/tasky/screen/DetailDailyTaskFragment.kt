package com.example.tasky.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasky.R
import com.example.tasky.databinding.FragmentDetailDailyTaskBinding
import com.example.tasky.viewmodel.DailyTaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class DetailDailyTaskFragment : Fragment() {

    private var _binding: FragmentDetailDailyTaskBinding? = null
    private val binding get() = _binding!!
    val notes by navArgs<DetailDailyTaskFragmentArgs>()
    val viewModel: DailyTaskViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDetailDailyTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.titleTv.text = notes.data.title
        binding.note.text = notes.data.note
        binding.date.text = notes.data.date
        binding.start.text = notes.data.timeStart
        binding.end.text = notes.data.timeEnd


        binding.deleteBtn.setOnClickListener {
            val bottomSheet: BottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
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

        binding.editBtn.setOnClickListener {
           findNavController().navigate(R.id.updateDailyTaskFragment)
        }
    }

}