package com.example.tasky.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tasky.R
import com.example.tasky.databinding.FragmentUpdateDailyTaskBinding


class UpdateDailyTaskFragment : Fragment() {

    private var _binding: FragmentUpdateDailyTaskBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentUpdateDailyTaskBinding.inflate(inflater, container, false)
        val root:View = binding.root
        return root
    }


}