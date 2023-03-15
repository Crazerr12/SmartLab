package com.example.smartlab.presentation.entrance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.FragmentPasswordBinding

class PasswordFragment : Fragment() {

    lateinit var binding: FragmentPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordBinding.inflate(inflater, container, false)

        val sharedPref = SharedPrefUserStorage(requireContext())
        val navigation = this.findNavController()
        val listOfIndicators = listOf(
            binding.indicator1,
            binding.indicator2,
            binding.indicator3,
            binding.indicator4,
        )
        val password = arrayOfNulls<String>(4)
        var currentIndex = 0

        binding.textSkip.setOnClickListener {
            navigation.navigate(R.id.action_passwordFragment_to_profileCardFragment)
        }

        binding.number1.setOnClickListener {
            currentIndex =
                setPassword(password, currentIndex, listOfIndicators, navigation, binding.number1)
        }
        binding.number2.setOnClickListener {
            currentIndex =
                setPassword(password, currentIndex, listOfIndicators, navigation, binding.number2)
        }
        binding.number3.setOnClickListener {
            currentIndex =
                setPassword(password, currentIndex, listOfIndicators, navigation, binding.number3)
        }
        binding.number4.setOnClickListener {
            currentIndex =
                setPassword(password, currentIndex, listOfIndicators, navigation, binding.number4)
        }
        binding.number5.setOnClickListener {
            currentIndex =
                setPassword(password, currentIndex, listOfIndicators, navigation, binding.number5)
        }
        binding.number6.setOnClickListener {
            currentIndex =
                setPassword(password, currentIndex, listOfIndicators, navigation, binding.number6)
        }
        binding.number7.setOnClickListener {
            currentIndex =
                setPassword(password, currentIndex, listOfIndicators, navigation, binding.number7)
        }
        binding.number8.setOnClickListener {
            currentIndex =
                setPassword(password, currentIndex, listOfIndicators, navigation, binding.number8)
        }
        binding.number9.setOnClickListener {
            currentIndex =
                setPassword(password, currentIndex, listOfIndicators, navigation, binding.number9)
        }
        binding.number0.setOnClickListener {
            currentIndex =
                setPassword(password, currentIndex, listOfIndicators, navigation, binding.number0)
        }
        binding.erase.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                password[currentIndex] = null
                listOfIndicators[currentIndex].setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_password_inactive
                    )
                )
            }
        }
        return binding.root
    }

    private fun setPassword(
        password: Array<String?>,
        currentIndex: Int,
        listOfIndicators: List<ImageView>,
        navigation: NavController,
        textView: TextView
    ): Int {
        var curIndex = currentIndex
        password[curIndex] = textView.text.toString()
        listOfIndicators[curIndex].setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.indicator_password_active
            )
        )
        curIndex++
        if (curIndex == password.size)
            navigation.navigate(R.id.action_passwordFragment_to_profileCardFragment)
        return curIndex
    }
}