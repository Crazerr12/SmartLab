package com.example.smartlab.presentation.entrance

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.data.api.RetrofitInstance
import com.example.smartlab.databinding.FragmentEntranceBinding
import com.example.smartlab.presentation.common.emailValid
import kotlinx.coroutines.launch

class EntranceFragment : Fragment() {

    lateinit var binding: FragmentEntranceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEntranceBinding.inflate(inflater, container, false)

        val navigation = this.findNavController()
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                val isEnable = emailValid(p0)
                binding.nextButton.isEnabled = isEnable
            }
        }

        binding.emailEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.emailEdit.clearFocus() // Скрыть курсор
                true
            }
            false
        }

        binding.emailEdit.addTextChangedListener(watcher)

        binding.nextButton.setOnClickListener {
            val email = binding.emailEdit.text.toString()
            CodeFromEmailFragment.email = email
            navigation.navigate(R.id.action_entranceFragment_to_codeFromEmailFragment)
            lifecycleScope.launch {
                val msg = RetrofitInstance.retrofit.sendCode(email).code()
                if (msg == 422)
                    Toast.makeText(requireContext(), "Ошибка", Toast.LENGTH_SHORT).show()
                else if (msg == 200)
                    Toast.makeText(requireContext(), "Окей", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}