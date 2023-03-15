package com.example.smartlab.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment(): Fragment() {

    open val showBottomNavigationView: Boolean
        get() = (parentFragment as? BaseFragment)?.showBottomNavigationView ?: false

    private var bottomNavigationManager: BottomNavigationManager? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationManager){
            bottomNavigationManager = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationManager?.setNavigationViewVisibility(showBottomNavigationView)
    }
}