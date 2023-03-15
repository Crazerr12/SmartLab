package com.example.smartlab.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.smartlab.R
import com.example.smartlab.databinding.ActivityMajorBinding
import com.example.smartlab.presentation.base.BottomNavigationManager

class MajorActivity : AppCompatActivity(), BottomNavigationManager {

    lateinit var binding: ActivityMajorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMajorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController =
            (supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment).navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun setNavigationViewVisibility(isVisible: Boolean) {
        binding.bottomNavigationView.isVisible = isVisible
    }
}