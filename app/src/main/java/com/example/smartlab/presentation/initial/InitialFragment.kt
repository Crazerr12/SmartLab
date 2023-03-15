package com.example.smartlab.presentation.initial

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.HandlerCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.FragmentInitialBinding


// Класс, который соответствует экрану "Заставка"
//25.02.2023 Фадеев Антон Алексеевич

class InitialFragment : Fragment() {

    lateinit var binding: FragmentInitialBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInitialBinding.inflate(inflater, container, false)

        val sharedPreferences = SharedPrefUserStorage(requireContext())
        val navigation = this.findNavController()

        val handler = HandlerCompat.createAsync(Looper.getMainLooper())
        handler.postDelayed({
            if (sharedPreferences.getOnBoarding())
                navigation.navigate(R.id.entranceFragment)
            else
                navigation.navigate(R.id.action_initialFragment_to_onBoardingViewPagerFragment)
        }, 4000L)

        return binding.root
    }
}