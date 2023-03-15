package com.example.smartlab.presentation.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.smartlab.R
import com.example.smartlab.data.api.RetrofitInstance
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.FragmentProfileBinding
import com.example.smartlab.presentation.base.BaseFragment
import com.example.smartlab.presentation.models.UpdateProfileModel
import kotlinx.coroutines.launch

class ProfileFragment : BaseFragment() {

    override val showBottomNavigationView = true
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val genders = resources.getStringArray(R.array.genders)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genders)
        binding.textAutoComplete.setAdapter(arrayAdapter)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (
                    binding.nameEdit.text!!.isNotEmpty()
                    && binding.surnameEdit.text!!.isNotEmpty()
                    && binding.patronymicEdit.text!!.isNotEmpty()
                    && binding.dateOfBirthEdit.text!!.isNotEmpty()
                    && binding.textAutoComplete.text!!.isNotEmpty()
                ) binding.saveButton.isEnabled = true
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        binding.nameEdit.addTextChangedListener(textWatcher)

        binding.surnameEdit.addTextChangedListener(textWatcher)

        binding.patronymicEdit.addTextChangedListener(textWatcher)

        binding.dateOfBirthEdit.addTextChangedListener(textWatcher)

        binding.textAutoComplete.addTextChangedListener(textWatcher)

        binding.saveButton.setOnClickListener {
            val user = UpdateProfileModel(
                firstname = binding.nameEdit.text.toString(),
                lastname = binding.surnameEdit.text.toString(),
                middlename = binding.patronymicEdit.text.toString(),
                bith = binding.dateOfBirthEdit.text.toString(),
                pol = binding.textAutoComplete.text.toString(),
            )
            val token = SharedPrefUserStorage(requireContext()).getToken()
            lifecycleScope.launch {
                RetrofitInstance.retrofit.updateProfile("Bearer $token", user).body()
            }
            SharedPrefUserStorage(requireContext()).saveUser(user)
        }

        return binding.root
    }
}