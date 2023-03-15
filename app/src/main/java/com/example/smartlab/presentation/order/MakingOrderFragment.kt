package com.example.smartlab.presentation.order

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.smartlab.R
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.*
import com.example.smartlab.presentation.base.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog


class MakingOrderFragment : BaseFragment() {

    private lateinit var dialog: BottomSheetDialog
    override val showBottomNavigationView: Boolean = false
    lateinit var binding: FragmentMakingOrderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMakingOrderBinding.inflate(inflater, container, false)

        val orderAdapter = OrderAdapter(resources, binding)
        binding.recyclerView.adapter = orderAdapter
        val navigation = this.findNavController()
        var countOfUsers = 1
        var countOfAnalyses = 0
        val productList = SharedPrefUserStorage(requireContext()).getProductsList()
        var price = 0U
        for (product in productList) {
            price += product.price.toUInt()
        }

        fun check(): Boolean {
            binding.apply {
                return (addressEdit.text!!.isNotEmpty()
                        && dataTimeEdit.text!!.isNotEmpty()
                        && phoneEdit.text!!.isNotEmpty())
            }
        }

        fun checkWithoutRecycler(): Boolean = check() && binding.textAutoComplete.text.isNotEmpty()


        fun checkWithRecycler(): Boolean =
            check() && !orderAdapter.getListChangedEditText().contains(false)


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (binding.recyclerView.isVisible) {
                    binding.buttonOrder.isEnabled = checkWithRecycler()
                } else binding.buttonOrder.isEnabled = checkWithoutRecycler()
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        binding.apply {
            addressEdit.addTextChangedListener(textWatcher)
            phoneEdit.addTextChangedListener(textWatcher)
            textAutoComplete.addTextChangedListener(textWatcher)
            dataTimeEdit.addTextChangedListener(textWatcher)
        }

        binding.buttonBack.setOnClickListener {
            navigation.popBackStack()
        }

        binding.addressEdit.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.clearFocus()
                createAddressDialog(binding)
            }
        }

        binding.dataTimeEdit.onFocusChangeListener = View.OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                view.clearFocus()
                createDataTimeDialog(binding)
            }

        }

        binding.textAutoComplete.setOnClickListener {
            createChoosePatientDialog(binding)
        }

        binding.textAutoCompleteInput.setEndIconOnClickListener {
            createChoosePatientDialog(binding)
        }

        binding.countOfAnalyses.text = "${productList.size} анализа"

        binding.price.text = price.toString()

        orderAdapter.apply {
            removeCard = {
                countOfUsers--
                if (countOfUsers == 1) {
                    binding.recyclerView.isVisible = false
                    binding.textAutoCompleteInput.isVisible = true
                    price = 0U
                    binding.buttonOrder.isEnabled = checkWithoutRecycler()
                    countOfAnalyses = productList.size
                    for (product in productList) {
                        price += product.price.toUInt()
                    }
                    binding.countOfAnalyses.text = "$countOfAnalyses анализа"
                    binding.price.text = "$price ${resources.getString(R.string.ruble)}"

                } else {
                    price = 0U
                    countOfAnalyses = 0
                    deleteItem()
                }
            }

            choosePatient = { itemOrderBinding ->
                createChoosePatientDialog(itemOrderBinding)
                itemOrderBinding.userNameTextAutoComplete.addTextChangedListener(textWatcher)
            }

            sumOfAnalyses = { summa ->
                price += summa.toUInt()
                countOfAnalyses++
                binding.countOfAnalyses.text = "$countOfAnalyses анализа"
                binding.price.text = "$price ${resources.getString(R.string.ruble)}"
            }

            removeCheck = { summa ->
                price -= summa.toUInt()
                countOfAnalyses--
                binding.countOfAnalyses.text = "$countOfAnalyses анализа"
                binding.price.text = "$price ${resources.getString(R.string.ruble)}"
            }

            addCheck = { summa ->
                price += summa.toUInt()
                countOfAnalyses++
                binding.countOfAnalyses.text = "$countOfAnalyses анализа"
                binding.price.text = "$price ${resources.getString(R.string.ruble)}"
            }
        }

        binding.buttonAddPatient.setOnClickListener {
            binding.recyclerView.isVisible = true
            countOfUsers++
            binding.textAutoCompleteInput.isVisible = false
            orderAdapter.submitList(countOfUsers)
            price = 0U
            countOfAnalyses = 0
            binding.buttonOrder.isEnabled = false
        }

        binding.buttonOrder.setOnClickListener { navigation.navigate(R.id.action_makingOrderFragment_to_paymentFragment) }

        return binding.root
    }

    private fun createAddressDialog(binding: FragmentMakingOrderBinding) {
        val addressBinding = FragmentDialogAddressBinding.inflate(layoutInflater)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(addressBinding.root)
        dialog.show()

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!addressBinding.nameOfAddressInput.isVisible) {
                    if (addressBinding.addressEdit.text!!.isNotEmpty()
                        && addressBinding.doorwayEdit.text!!.isNotEmpty()
                        && addressBinding.flatEdit.text!!.isNotEmpty()
                        && addressBinding.longitudeEdit.text!!.isNotEmpty()
                        && addressBinding.widthEdit.text!!.isNotEmpty()
                        && addressBinding.heightEdit.text!!.isNotEmpty()
                        && addressBinding.intercomEdit.text!!.isNotEmpty()
                    ) {
                        addressBinding.submitButton.isEnabled = true
                    }
                } else {
                    if (addressBinding.addressEdit.text!!.isNotEmpty()
                        && addressBinding.doorwayEdit.text!!.isNotEmpty()
                        && addressBinding.flatEdit.text!!.isNotEmpty()
                        && addressBinding.longitudeEdit.text!!.isNotEmpty()
                        && addressBinding.widthEdit.text!!.isNotEmpty()
                        && addressBinding.heightEdit.text!!.isNotEmpty()
                        && addressBinding.intercomEdit.text!!.isNotEmpty()
                        && addressBinding.nameOfAddressEdit.text!!.isNotEmpty()
                    ) {
                        addressBinding.submitButton.isEnabled = true
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        }

        addressBinding.switchCompat.setOnCheckedChangeListener { _, isChecked ->
            addressBinding.nameOfAddressInput.isVisible = isChecked
        }

        addressBinding.addressEdit.addTextChangedListener(textWatcher)
        addressBinding.longitudeEdit.addTextChangedListener(textWatcher)
        addressBinding.widthEdit.addTextChangedListener(textWatcher)
        addressBinding.heightEdit.addTextChangedListener(textWatcher)
        addressBinding.flatEdit.addTextChangedListener(textWatcher)
        addressBinding.doorwayEdit.addTextChangedListener(textWatcher)
        addressBinding.floorEdit.addTextChangedListener(textWatcher)
        addressBinding.nameOfAddressEdit.addTextChangedListener(textWatcher)

        addressBinding.submitButton.setOnClickListener {
            binding.addressEdit.setText("${addressBinding.addressEdit.text}, кв.${addressBinding.doorwayEdit.text}")
            dialog.cancel()
        }
    }

    private fun createDataTimeDialog(binding: FragmentMakingOrderBinding) {
        val dataBinding = FragmentDialogDataTimeBinding.inflate(layoutInflater)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dataBinding.root)
        dialog.show()

        fun resetButtonSelection() {
            dataBinding.apply {
                buttonTime10.isChecked = false
                buttonTime13.isChecked = false
                buttonTime14.isChecked = false
                buttonTime15.isChecked = false
                buttonTime16.isChecked = false
                buttonTime18.isChecked = false
                buttonTime19.isChecked = false
            }
        }

        fun onClick(button: RadioButton) {
            resetButtonSelection()

            button.isChecked = true
        }

        dataBinding.close.setOnClickListener { dialog.cancel() }

        dataBinding.buttonTime10.setOnClickListener {
            onClick(dataBinding.buttonTime10)
            dataBinding.buttonSubmit.isEnabled = true
        }
        dataBinding.buttonTime13.setOnClickListener {
            onClick(dataBinding.buttonTime13)
            dataBinding.buttonSubmit.isEnabled = true
        }
        dataBinding.buttonTime14.setOnClickListener {
            onClick(dataBinding.buttonTime14)
            dataBinding.buttonSubmit.isEnabled = true
        }
        dataBinding.buttonTime15.setOnClickListener {
            onClick(dataBinding.buttonTime15)
            dataBinding.buttonSubmit.isEnabled = true
        }
        dataBinding.buttonTime16.setOnClickListener {
            onClick(dataBinding.buttonTime16)
            dataBinding.buttonSubmit.isEnabled = true
        }
        dataBinding.buttonTime18.setOnClickListener {
            onClick(dataBinding.buttonTime18)
            dataBinding.buttonSubmit.isEnabled = true
        }
        dataBinding.buttonTime19.setOnClickListener {
            onClick(dataBinding.buttonTime19)
            dataBinding.buttonSubmit.isEnabled = true
        }

        val list = listOf(
            dataBinding.buttonTime10,
            dataBinding.buttonTime13,
            dataBinding.buttonTime14,
            dataBinding.buttonTime15,
            dataBinding.buttonTime16,
            dataBinding.buttonTime18,
            dataBinding.buttonTime19,
        )

        dataBinding.buttonSubmit.setOnClickListener {
            for (button in list) {
                if (button.isChecked) {
                    binding.dataTimeEdit.setText("${dataBinding.textAutoComplete.text} ${button.text}")
                    dialog.cancel()
                }
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun createChoosePatientDialog(binding: FragmentMakingOrderBinding) {
        val patientBinding = FragmentDialogChoosePatientBinding.inflate(layoutInflater)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(patientBinding.root)
        dialog.show()

        val userList = SharedPrefUserStorage(requireContext()).getUserList()
        val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        layoutParams.setMargins(0, resources.getDimensionPixelSize(R.dimen.spacing_16dp), 0, 0)
        val buttons = mutableListOf<RadioButton>()
        val twelve = resources.getDimensionPixelSize(R.dimen.spacing_12dp)
        val fourteen = resources.getDimensionPixelSize(R.dimen.spacing_14dp)

        for (user in userList) {
            val name = "${user.firstname} ${user.lastname}"
            if (binding.textAutoComplete.text.contains(name))
                null
            else {
                val button = RadioButton(requireContext())
                button.text = name
                if (user.pol == "Мужской")
                    button.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.male
                        ), null, null, null
                    )
                else button.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.female
                    ), null, null, null
                )
                button.buttonDrawable = null
                button.compoundDrawablePadding = twelve
                button.setBackgroundResource(R.drawable.button_selector)
                button.setPadding(twelve, fourteen, 0, fourteen)
                button.setTextColor(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.drawable.button_text_selector
                    )
                )
                button.layoutParams = layoutParams
                button.buttonTintList = ColorStateList.valueOf(Color.TRANSPARENT)
                patientBinding.linearLayout.addView(button)
                buttons.add(button)
            }
        }

        var name = ""
        var drawableStart: Drawable? = null

        for (button in buttons) {
            button.setOnClickListener {
                for (btn in buttons)
                    btn.isChecked = false
                button.isChecked = true
                name = button.text.toString()
                val drawables = button.compoundDrawables
                drawableStart = drawables[0]
                patientBinding.buttonSubmit.isEnabled = true
            }
        }

        patientBinding.buttonSubmit.setOnClickListener {
            binding.textAutoComplete.setText(name)
            binding.textAutoComplete.compoundDrawablePadding = twelve
            binding.textAutoComplete.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawableStart,
                null,
                null,
                null
            )
            dialog.cancel()
        }

        patientBinding.close.setOnClickListener {
            dialog.cancel()
        }

    }

    @SuppressLint("ResourceType")
    private fun createChoosePatientDialog(binding: ItemOrderBinding) {
        val patientBinding = FragmentDialogChoosePatientBinding.inflate(layoutInflater)
        dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(patientBinding.root)
        dialog.show()

        val userList = SharedPrefUserStorage(requireContext()).getUserList()
        val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        layoutParams.setMargins(0, resources.getDimensionPixelSize(R.dimen.spacing_16dp), 0, 0)
        val buttons = mutableListOf<RadioButton>()
        val twelve = resources.getDimensionPixelSize(R.dimen.spacing_12dp)
        val fourteen = resources.getDimensionPixelSize(R.dimen.spacing_14dp)

        for (user in userList) {
            val name = "${user.firstname} ${user.lastname}"
            if (binding.userNameTextAutoComplete.text.contains(name))
                null
            else {
                val button = RadioButton(requireContext())
                button.text = name
                if (user.pol == "Мужской")
                    button.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.male
                        ), null, null, null
                    )
                else button.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.female
                    ), null, null, null
                )
                button.buttonDrawable = null
                button.compoundDrawablePadding = twelve
                button.setBackgroundResource(R.drawable.button_selector)
                button.setPadding(twelve, fourteen, 0, fourteen)
                button.setTextColor(
                    ContextCompat.getColorStateList(
                        requireContext(),
                        R.drawable.button_text_selector
                    )
                )
                button.layoutParams = layoutParams
                button.buttonTintList = ColorStateList.valueOf(Color.TRANSPARENT)
                patientBinding.linearLayout.addView(button)
                buttons.add(button)
            }
        }

        var name = ""
        var drawableStart: Drawable? = null

        for (button in buttons) {
            button.setOnClickListener {
                for (btn in buttons)
                    btn.isChecked = false
                button.isChecked = true
                name = button.text.toString()
                val drawables = button.compoundDrawables
                drawableStart = drawables[0]
                patientBinding.buttonSubmit.isEnabled = true
            }
        }

        patientBinding.buttonSubmit.setOnClickListener {
            binding.userNameTextAutoComplete.setText(name)
            binding.userNameTextAutoComplete.compoundDrawablePadding = twelve
            binding.userNameTextAutoComplete.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawableStart,
                null,
                null,
                null
            )
            dialog.cancel()
        }

        patientBinding.close.setOnClickListener {
            dialog.cancel()
        }
    }
}