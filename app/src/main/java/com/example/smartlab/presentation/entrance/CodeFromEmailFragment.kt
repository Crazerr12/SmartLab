package com.example.smartlab.presentation.entrance

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.data.api.RetrofitInstance
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.FragmentCodeFromEmailBinding
import com.example.smartlab.presentation.models.SignInModel
import kotlinx.coroutines.launch

class CodeFromEmailFragment : Fragment() {

    lateinit var countDownTimer: CountDownTimer
    lateinit var binding: FragmentCodeFromEmailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodeFromEmailBinding.inflate(inflater, container, false)

        startTimer()
        val navigation = this.findNavController()

        binding.buttonBack.setOnClickListener {
            navigation.popBackStack()
        }

        binding.firstDigitEdit.requestFocus()

        binding.firstDigitEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length == 1)
                    binding.secondDigitEdit.requestFocus()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.secondDigitEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length == 1)
                    binding.thirdDigitEdit.requestFocus()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.thirdDigitEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length == 1)
                    binding.fourthDigitEdit.requestFocus()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.fourthDigitEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val code: String =
                    binding.firstDigitEdit.text.toString() +
                            binding.secondDigitEdit.text.toString() +
                            binding.thirdDigitEdit.text.toString() +
                            binding.fourthDigitEdit.text.toString()
                if (code.length == 4) {
                    val signIn = SignInModel(
                        email,
                        code.toInt()
                    )
                    lifecycleScope.launch {
                        if (RetrofitInstance.retrofit.signIn(signIn.email, signIn.code)
                                .code() == 200
                        ) {
                            Toast.makeText(requireContext(), "Окей", Toast.LENGTH_SHORT).show()
                            val token =
                                RetrofitInstance.retrofit.signIn(signIn.email, signIn.code).body()
                            token?.let { SharedPrefUserStorage(requireContext()).saveToken(it.token) }
                            navigation.navigate(R.id.action_codeFromEmailFragment_to_passwordFragment)
                        } else Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view?.findFocus(), InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onPause() {
        super.onPause()
        countDownTimer.cancel()
    }

    companion object {
        var email: String = ""
    }

    private fun startTimer() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(p0: Long) {
                val secondsLeft = p0 / 1000
                binding.timer.text = "Отправить код повторно\nможно будет через $secondsLeft секунд"
                if (secondsLeft == 1L)
                    lifecycleScope.launch { RetrofitInstance.retrofit.sendCode(email).body() }
            }

            override fun onFinish() {
                startTimer()
            }
        }.start()
    }
}