package com.example.smartlab.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.smartlab.R
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.FragmentOnBoardingViewPagerBinding

class OnBoardingViewPagerFragment : Fragment() {

    lateinit var binding: FragmentOnBoardingViewPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnBoardingViewPagerBinding.inflate(inflater, container, false)

        val sharedPreferences = SharedPrefUserStorage(requireContext())
        val navigation = this.findNavController()

        val drawables = listOf(
            context?.let { ContextCompat.getDrawable(it, R.drawable.illustration1) }!!,
            context?.let { ContextCompat.getDrawable(it, R.drawable.illustration2) }!!,
            context?.let { ContextCompat.getDrawable(it, R.drawable.illustration3) }!!
        )

        val adapter = ViewPager(this, drawables)
        val indicators = arrayOfNulls<ImageView>(adapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(context)
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicatorinactive
                    )
                )
                it.layoutParams = layoutParams
                binding.indicatorsContainer.addView(it)
            }
        }

        binding.viewPager.adapter = adapter

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2)
                    binding.textSkip.text = "Завершить"
                else binding.textSkip.text = "Пропустить"
                val childCount = binding.indicatorsContainer.childCount
                for (i in 0 until childCount) {
                    val imageView = binding.indicatorsContainer.getChildAt(i) as ImageView
                    if (i == position) {
                        imageView.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(), R.drawable.indicatoractive
                            )
                        )
                    } else {
                        imageView.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(), R.drawable.indicatorinactive
                            )
                        )
                    }
                }
            }
        })

        binding.textSkip.setOnClickListener{
            sharedPreferences.saveOnBoarding(true)
            navigation.navigate(R.id.action_onBoardingViewPagerFragment_to_entranceFragment)
        }

        return binding.root
    }
}