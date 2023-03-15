package com.example.smartlab.presentation.order

import android.content.Context
import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.RecyclerView
import com.example.smartlab.R
import com.example.smartlab.data.preferences.SharedPrefUserStorage
import com.example.smartlab.databinding.FragmentMakingOrderBinding
import com.example.smartlab.databinding.ItemOrderBinding

class OrderViewHolder(private val binding: ItemOrderBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        resources: Resources,
        position: Int,
        orderBinding: FragmentMakingOrderBinding,
        removeCard: () -> Unit,
        choosePatient: (ItemOrderBinding) -> Unit,
        sumOfAnalyses: (Int) -> Unit,
        removeCheck: (Int) -> Unit,
        addCheck: (Int) -> Unit,
        notEmptyList: MutableList<Boolean>,
    ) = with(binding) {
        val analysesAdapter = AnalysesAdapter(resources)
        recyclerView.adapter = analysesAdapter
        analysesAdapter.apply {
            countOfAnalyses = { price ->
                sumOfAnalyses(price)
            }

            removeAnalyses = { price ->
                removeCheck(price)
            }

            addAnalyses = { price ->
                addCheck(price)
            }
        }

        if (position == 0) {
            val drawables = orderBinding.textAutoComplete.compoundDrawables
            val drawable = drawables[0]
            userNameTextAutoComplete.setCompoundDrawablesRelativeWithIntrinsicBounds(
                drawable,
                null,
                null,
                null
            )
            userNameTextAutoComplete.text = orderBinding.textAutoComplete.text
            userNameTextAutoComplete.compoundDrawablePadding =
                resources.getDimensionPixelSize(R.dimen.spacing_12dp)
        }
        close.setOnClickListener {
            removeCard()
        }

        textInputLayout.setEndIconOnClickListener {
            choosePatient(binding)
        }

        userNameTextAutoComplete.setOnClickListener {
            choosePatient(binding)
        }

        userNameTextAutoComplete.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                notEmptyList[position] = userNameTextAutoComplete.text.isNotEmpty()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

        analysesAdapter.submitList(SharedPrefUserStorage(context).getProductsList())
    }
}