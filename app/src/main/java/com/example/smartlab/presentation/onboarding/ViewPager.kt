package com.example.smartlab.presentation.onboarding

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager(fragment: Fragment, private val drawables: List<Drawable>): FragmentStateAdapter(fragment) {
    override fun getItemCount() = drawables.size

    override fun createFragment(position: Int) = OnBoardingFragment(position, drawables)
}