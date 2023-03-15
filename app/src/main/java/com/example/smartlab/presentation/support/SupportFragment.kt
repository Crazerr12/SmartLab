package com.example.smartlab.presentation.support

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartlab.R
import com.example.smartlab.databinding.FragmentSupportBinding
import com.example.smartlab.presentation.base.BaseFragment

class SupportFragment : BaseFragment() {

    override val showBottomNavigationView = true
    lateinit var binding: FragmentSupportBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSupportBinding.inflate(inflater, container, false)

        return binding.root
    }
}