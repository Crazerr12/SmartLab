package com.example.smartlab.presentation.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentResultsBinding
import com.example.smartlab.presentation.base.BaseFragment

class ResultsFragment : BaseFragment() {

    override val showBottomNavigationView = true
    lateinit var binding:FragmentResultsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResultsBinding.inflate(inflater, container, false)



        return binding.root
    }
}