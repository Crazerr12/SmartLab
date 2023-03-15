package com.example.smartlab.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.smartlab.databinding.ActivityMainBinding
//Класс представляющий паттерн "single activity"
//25.02.2023 Фадеев Антон Алексеевич
//
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}