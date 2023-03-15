package com.example.smartlab.presentation.onboarding

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smartlab.databinding.FragmentOnBoardingBinding

//Класс, представляющий экран "OnBoard"
//25.02.2023 Фадеев Антон Алексеевич

class OnBoardingFragment(private val position: Int, private val drawables: List<Drawable>) :
    Fragment() {

    lateinit var binding: FragmentOnBoardingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        binding.apply {
            when (position) {
                1 -> {
                    headingOnBoarding.text = "Уведомления"
                    infoOnBoarding.text = "Вы быстро узнаете о результатах"
                    imageOnBoarding.setImageDrawable(drawables[position])
                }
                2 -> {
                    headingOnBoarding.text = "Мониторинг"
                    infoOnBoarding.text =
                        "Наши врачи всегда наблюдают\nза вашими показателями здоровья"
                    imageOnBoarding.setImageDrawable(drawables[position])
                }
            }
        }
        return binding.root
    }
}