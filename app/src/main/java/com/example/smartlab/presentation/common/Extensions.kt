package com.example.smartlab.presentation.common

import android.text.Editable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

fun Fragment.emailValid(email: Editable?): Boolean{
        val emailPattern = "^[A-z0-9]+@[a-z]+\\.[a-z]{1,3}\$".toRegex()
        return email?.let { emailPattern.matches(it) }!!
    }